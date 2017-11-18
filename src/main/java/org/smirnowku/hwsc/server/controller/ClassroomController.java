package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.dto.ClassroomDto;
import org.smirnowku.hwsc.server.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{userId}/classroom")
@CrossOrigin
public class ClassroomController {

    @Resource
    private ClassroomService service;

    @PostMapping
    public ResponseEntity createClassroom(@PathVariable long userId, @RequestBody ClassroomDto classroomDto) {
        //service.createClassroom(userId, classroomDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/add-members")
    public ResponseEntity addMembers(@PathVariable long userId, @PathVariable long id,
                                     @RequestParam(required = false) long[] studentsIds,
                                     @RequestParam(required = false) long[] teachersIds) {
        service.addMembers(id, studentsIds, teachersIds);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity editClassroom(@PathVariable long userId, @PathVariable long id,
                                        @RequestBody ClassroomDto classroomDto) {
        //
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<List<Classroom>> getClassroomsAsStudent(@PathVariable long userId) {
        return new ResponseEntity<>(service.getClassroomsAsStudent(userId), HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<Classroom>> getClassroomsAsTeacher(@PathVariable long userId) {
        return new ResponseEntity<>(service.getClassroomsAsTeacher(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroom(@PathVariable long userId, @PathVariable long id) {
        return new ResponseEntity<>(service.getClassroom(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/homeworks")
    public ResponseEntity<List<Homework>> getHomeworks(@PathVariable long userId, @PathVariable long id) {
        return new ResponseEntity<>(service.getHomeworks(id), HttpStatus.OK);
    }
}
