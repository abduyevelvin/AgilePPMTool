package io.agileintelligence.service;

import io.agileintelligence.model.Project;
import io.agileintelligence.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements  IProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {
        //Todo: add Logic
        return projectRepository.save(project);
    }
}
