package org.smirnowku.hwsc.rest.controller;

import org.smirnowku.hwsc.core.service.impl.AssignmentService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/assignment")
public class AssignmentController {

    @Resource
    private AssignmentService service;

    @PutMapping("/{id}")
    public ResponseEntity submit(@PathVariable String username, @PathVariable long id) {
        service.submit(username, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<AssignmentDto>> getToDo(@PathVariable String username) {
        return new ResponseEntity<>(service.getToDo(username), HttpStatus.OK);
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<AssignmentDto>> getSubmitted(@PathVariable String username) {
        return new ResponseEntity<>(service.getSubmitted(username), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<AssignmentDto>> getCompleted(@PathVariable String username) {
        return new ResponseEntity<>(service.getCompleted(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }
}
