package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.Assignment;
import org.smirnowku.hwsc.server.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/assignment")
@CrossOrigin
public class AssignmentController {

    @Resource
    private AssignmentService service;

    @PutMapping("/{id}")
    public ResponseEntity submit(@PathVariable String username, @PathVariable long id) {
        service.submit(username, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Assignment>> getToDo(@PathVariable String username) {
        return new ResponseEntity<>(service.getToDo(username), HttpStatus.OK);
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<Assignment>> getSubmitted(@PathVariable String username) {
        return new ResponseEntity<>(service.getSubmitted(username), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Assignment>> getCompleted(@PathVariable String username) {
        return new ResponseEntity<>(service.getCompleted(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }
}
