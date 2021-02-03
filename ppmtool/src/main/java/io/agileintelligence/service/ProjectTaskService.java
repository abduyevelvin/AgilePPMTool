package io.agileintelligence.service;

import io.agileintelligence.model.ProjectTask;
import io.agileintelligence.repository.BacklogRepository;
import io.agileintelligence.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService implements IProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository backlogRepository;

    @Autowired
    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public ProjectTask addProjectTask() {
        return null;
    }
}
