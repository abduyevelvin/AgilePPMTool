package io.agileintelligence.service;

import io.agileintelligence.model.Project;

public interface IProjectService {

    Project saveOrUpdateProject(Project project, String username);
    Project findProjectByProjectIdentifier(String projectId, String username);
    Iterable<Project> findALlProjects(String username);
    void deleteProjectByProjectIdentifier(String projectId, String username);
}
