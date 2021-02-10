package io.agileintelligence.service;

import io.agileintelligence.exception.ProjectIdException;
import io.agileintelligence.exception.ProjectNotFoundException;
import io.agileintelligence.model.Backlog;
import io.agileintelligence.model.Project;
import io.agileintelligence.model.User;
import io.agileintelligence.repository.BacklogRepository;
import io.agileintelligence.repository.ProjectRepository;
import io.agileintelligence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements  IProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project, String username) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if ((existingProject != null) && (!existingProject.getProjectLeader().equalsIgnoreCase(username))) {
                throw new ProjectNotFoundException(String.format("Project ID: '%s' doesn't exist in your account!", project.getProjectIdentifier()));
            } else if (existingProject == null) {
                throw new ProjectNotFoundException(String.format("Project ID: '%s' cannot be updated, because doesn't exist!", project.getProjectIdentifier()));
            }
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(username);
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
    public Project findProjectByProjectIdentifier(String projectId, String username) {
        projectId = projectId.toUpperCase();
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(String.format("Project ID: '%s' doesn't exist!", projectId));
        }

        if(!project.getProjectLeader().equalsIgnoreCase(username)) {
            throw new ProjectNotFoundException(String.format("Project ID: '%s' doesn't exist in your account!", projectId));
        }

        return project;
    }

    @Override
    public Iterable<Project> findALlProjects(String username) {

        return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByProjectIdentifier(String projectId, String username) {
        Project project = findProjectByProjectIdentifier(projectId, username);

        projectRepository.delete(project);
    }
}
