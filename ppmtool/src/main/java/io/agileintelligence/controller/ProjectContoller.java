package io.agileintelligence.controller;

import io.agileintelligence.model.Project;
import io.agileintelligence.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectContoller {

    private IProjectService projectService;

    @Autowired
    public ProjectContoller(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {
        Project created = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
