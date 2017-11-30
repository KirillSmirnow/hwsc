package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.Classroom;
import org.smirnowku.hwsc.model.Homework;
import org.smirnowku.hwsc.model.dto.ClassroomDto;
import org.smirnowku.hwsc.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/classroom")
@CrossOrigin
public class ClassroomController {

    @Resource
    private ClassroomService service;

    @PostMapping
    public ResponseEntity create(@PathVariable String username, @RequestBody ClassroomDto dto) {
        service.create(username, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/add-members")
    public ResponseEntity addMembers(@PathVariable String username, @PathVariable long id,
                                     @RequestParam(required = false) List<String> studentsUsernames,
                                     @RequestParam(required = false) List<String> teachersUsernames) {
        service.addMembers(username, id, studentsUsernames, teachersUsernames);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable String username, @PathVariable long id,
                               @RequestBody ClassroomDto dto) {
        service.edit(username, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<List<Classroom>> getClassroomsAsStudent(@PathVariable String username) {
        return new ResponseEntity<>(service.getClassroomsAsStudent(username), HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<Classroom>> getClassroomsAsTeacher(@PathVariable String username) {
        return new ResponseEntity<>(service.getClassroomsAsTeacher(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }

    @GetMapping("/{id}/homeworks")
    public ResponseEntity<List<Homework>> getHomeworks(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.getHomeworks(username, id), HttpStatus.OK);
    }
}
