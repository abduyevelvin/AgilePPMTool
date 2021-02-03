package io.agileintelligence.controller;

import io.agileintelligence.model.Project;
import io.agileintelligence.service.IProjectService;
import io.agileintelligence.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectContoller {

    private IProjectService projectService;

    @Autowired
    public ProjectContoller(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = Util.mapValidationMethod(result);
        if (errorMap != null) {
            return errorMap;
        }

        Project created = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProjects() {

        return new ResponseEntity<>(projectService.findALlProjects(), HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = projectService.findProjectByProjectIdentifier(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectByProjectId(@PathVariable String projectId) {
        projectService.deleteProjectByProjectIdentifier(projectId);

        return new ResponseEntity<>("Project with ID: '" + projectId.toUpperCase() + "' was deleted!", HttpStatus.OK);
    }
}
