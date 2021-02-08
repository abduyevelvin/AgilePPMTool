package io.agileintelligence.service;

import io.agileintelligence.exception.ProjectIdException;
import io.agileintelligence.exception.ProjectNotFoundException;
import io.agileintelligence.model.Backlog;
import io.agileintelligence.model.Project;
import io.agileintelligence.repository.BacklogRepository;
import io.agileintelligence.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.NumberUp;

@Service
public class ProjectService implements  IProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        try {

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
            }

            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException(String.format("Project ID: '%s' is already exists!", project.getProjectIdentifier()));
        }
    }

    @Override
    public Project findProjectByProjectIdentifier(String projectId) {
        projectId = projectId.toUpperCase();
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(String.format("Project ID: '%s' doesn't exist!", projectId));
        }

        return project;
    }

    @Override
    public Iterable<Project> findALlProjects() {

        return projectRepository.findAll();
    }

    @Override
    public void deleteProjectByProjectIdentifier(String projectId) {
        Project project = findProjectByProjectIdentifier(projectId);

        projectRepository.delete(project);
    }
}
