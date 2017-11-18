package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{userId}/assignment")
@CrossOrigin
public class AssignmentController {

    @Resource
    private AssignmentService service;

    @PutMapping("/{id}")
    public ResponseEntity submitAssignment(@PathVariable long userId, @PathVariable long id) {
        //
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Assignment>> getAssignmentsToDo(@PathVariable long userId) {
        return new ResponseEntity<>(service.getAssignmentsToDo(userId), HttpStatus.OK);
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<Assignment>> getAssignmentsSubmitted(@PathVariable long userId) {
        return new ResponseEntity<>(service.getAssignmentsSubmitted(userId), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Assignment>> getAssignmentsCompleted(@PathVariable long userId) {
        return new ResponseEntity<>(service.getAssignmentsCompleted(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable long userId, @PathVariable long id) {
        //
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
