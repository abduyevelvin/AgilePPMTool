package io.agileintelligence.service;

import io.agileintelligence.model.ProjectTask;

public interface IProjectTaskService {

    ProjectTask addProjectTask(String projectId, ProjectTask projectTask);
    Iterable<ProjectTask> findTasksByProjectId(String projectId);
    ProjectTask findTaskByProjectSequence(String backlogId, String sequence);
    ProjectTask updateTaskByProjectSequence(ProjectTask updatedTask, String backlogId, String sequence);
    void deleteTaskByProjectIdentifier(String backlogId, String sequence);
}
