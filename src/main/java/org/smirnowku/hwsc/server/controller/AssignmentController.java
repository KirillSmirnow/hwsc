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
    public ResponseEntity submit(@PathVariable long userId, @PathVariable long id) {
        service.submit(userId, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Assignment>> getToDo(@PathVariable long userId) {
        return new ResponseEntity<>(service.getToDo(userId), HttpStatus.OK);
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<Assignment>> getSubmitted(@PathVariable long userId) {
        return new ResponseEntity<>(service.getSubmitted(userId), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Assignment>> getCompleted(@PathVariable long userId) {
        return new ResponseEntity<>(service.getCompleted(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> get(@PathVariable long userId, @PathVariable long id) {
        return new ResponseEntity<>(service.get(userId, id), HttpStatus.OK);
    }
}
