package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.controller.dto.ClassroomDto;
import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.Homework;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/classroom")
@CrossOrigin
public class ClassroomController {

    @Resource
    private ClassroomService classroomService;

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroom(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getClassroom(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<List<User>> getTeachers(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getTeachers(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<User>> getStudents(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getStudents(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/homeworks")
    public ResponseEntity<List<Homework>> getHomeworks(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getHomeworks(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createClassroom(@RequestParam long teacherId, @RequestBody ClassroomDto classroomDto) {
        classroomService.createClassroom(teacherId, classroomDto.name);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{classroomId}/add")
    public ResponseEntity addMembers(@PathVariable long classroomId,
                                     @RequestParam(required = false) long[] studentsIds,
                                     @RequestParam(required = false) long[] teachersIds) {
        classroomService.addMembers(classroomId, studentsIds, teachersIds);
        return new ResponseEntity(HttpStatus.OK);
    }
}
