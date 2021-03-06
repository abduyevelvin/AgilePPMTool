package io.agileintelligence.controller;

import io.agileintelligence.model.ProjectTask;
import io.agileintelligence.service.IProjectTaskService;
import io.agileintelligence.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private IProjectTaskService projectTaskService;

    @Autowired
    public BacklogController(IProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlogId,
                                            Principal principal) {
        ResponseEntity<?> errorMap = Util.mapValidationMethod(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask createdTask = projectTaskService.addProjectTask(backlogId, projectTask, principal.getName());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public ResponseEntity<?> getProjectTaskByBacklogId(@PathVariable String backlogId, Principal principal) {

        return new ResponseEntity<>(projectTaskService.findTasksByProjectId(backlogId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{backlogId}/{sequence}")
    public ResponseEntity<?> getProjectTasksBySequence(@PathVariable String backlogId, @PathVariable String sequence, Principal principal) {
        ProjectTask projectTask = projectTaskService.findTaskByProjectSequence(backlogId, sequence, principal.getName());

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{sequence}")
    public ResponseEntity<?> updateProjectTaskBySequence(@Valid @RequestBody ProjectTask updatedTask, BindingResult result,
                                                         @PathVariable String backlogId, @PathVariable String sequence, Principal principal) {
        ResponseEntity<?> errorMap = Util.mapValidationMethod(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask = projectTaskService.updateTaskByProjectSequence(updatedTask, backlogId, sequence, principal.getName());

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{sequence}")
    public ResponseEntity<?> deleteProjectTaskBySequence(@PathVariable String backlogId, @PathVariable String sequence, Principal principal) {
        projectTaskService.deleteTaskByProjectIdentifier(backlogId, sequence, principal.getName());

        return new ResponseEntity<>("Project with ID: '" + backlogId.toUpperCase() + "' was deleted!", HttpStatus.OK);
    }
}
