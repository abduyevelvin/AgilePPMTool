package io.agileintelligence.service;

import io.agileintelligence.exception.ProjectNotFoundException;
import io.agileintelligence.model.Backlog;
import io.agileintelligence.model.ProjectTask;
import io.agileintelligence.repository.BacklogRepository;
import io.agileintelligence.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService implements IProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository backlogRepository;
    private IProjectService projectService;

    @Autowired
    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository, IProjectService projectService) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
        this.projectService = projectService;
    }

    @Override
    public ProjectTask addProjectTask(String projectId, ProjectTask projectTask) {
        projectId = projectId.toUpperCase();

        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectId);

            projectTask.setBacklog(backlog);

            Integer backlogSeq = backlog.getPTSequence();
            backlogSeq++;
            backlog.setPTSequence(backlogSeq);

            projectTask.setProjectSequence(projectId + "-" + backlogSeq);
            projectTask.setProjectIdentifier(projectId);

            if (projectTask.getPriority() == null) /*|| projectTask.getPriority() == 0 in the future */{
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == null) /*projectTask.getStatus().equalsIgnoreCase("") || projectTask.getStatus().isEmpty() ||*/ {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception ex) {
            throw new ProjectNotFoundException(String.format("Project not found by ID: %s", projectId));
        }
    }

    @Override
    public Iterable<ProjectTask> findTasksByProjectId(String projectId) {
        projectId = projectId.toUpperCase();

        projectService.findProjectByProjectIdentifier(projectId);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectId);
    }

    @Override
    public ProjectTask findTaskByProjectSequence(String backlogId, String sequence) {
        backlogId = backlogId.toUpperCase();
        sequence = sequence.toUpperCase();

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlogId);
        if (backlog == null) {
            throw new ProjectNotFoundException(String.format("Project not found by ID: %s", backlogId));
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if (projectTask == null) {
            throw new ProjectNotFoundException(String.format("Project Task not found by ID: %s", sequence));
        }

        if(!projectTask.getProjectIdentifier().equalsIgnoreCase(backlogId)) {
            throw new ProjectNotFoundException(String.format("Project Task by ID: '%1$s' doesn't exist in Project: %2$s", sequence, backlogId));
        }

        return projectTask;
    }

    @Override
    public ProjectTask updateTaskByProjectSequence(ProjectTask updatedTask, String backlogId, String sequence) {
        ProjectTask projectTask = findTaskByProjectSequence(backlogId, sequence);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    @Override
    public void deleteTaskByProjectIdentifier(String backlogId, String sequence) {
        ProjectTask projectTask = findTaskByProjectSequence(backlogId, sequence);

        projectTaskRepository.delete(projectTask);
    }
}
