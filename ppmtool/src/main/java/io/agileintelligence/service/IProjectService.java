package io.agileintelligence.service;

import io.agileintelligence.model.Project;

public interface IProjectService {

    Project saveOrUpdateProject(Project project, String username);
    Project findProjectByProjectIdentifier(String projectId);
    Iterable<Project> findALlProjects();
    void deleteProjectByProjectIdentifier(String projectId);
}
