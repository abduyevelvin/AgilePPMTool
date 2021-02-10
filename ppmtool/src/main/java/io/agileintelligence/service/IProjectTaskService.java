package io.agileintelligence.service;

import io.agileintelligence.model.ProjectTask;

public interface IProjectTaskService {

    ProjectTask addProjectTask(String projectId, ProjectTask projectTask, String username);
    Iterable<ProjectTask> findTasksByProjectId(String projectId, String username);
    ProjectTask findTaskByProjectSequence(String backlogId, String sequence, String username);
    ProjectTask updateTaskByProjectSequence(ProjectTask updatedTask, String backlogId, String sequence, String username);
    void deleteTaskByProjectIdentifier(String backlogId, String sequence, String username);
}
