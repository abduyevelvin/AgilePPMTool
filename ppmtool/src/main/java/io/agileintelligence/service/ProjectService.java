package io.agileintelligence.service;

import io.agileintelligence.exception.ProjectIdException;
import io.agileintelligence.model.Project;
import io.agileintelligence.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.NumberUp;

@Service
public class ProjectService implements  IProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        try {
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID: '" + project.getProjectIdentifier() + "' is allready exists!");
        }
    }

    @Override
    public Project findProjectByProjectIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID: '" + projectId.toUpperCase() + "' doesn't exist!");
        }

        return project;
    }

    @Override
    public Iterable<Project> findALlProjects() {

        return projectRepository.findAll();
    }
}
