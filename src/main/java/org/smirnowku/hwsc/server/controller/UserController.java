package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.controller.dto.SignUpDto;
import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.model.Classroom;
import org.smirnowku.hwsc.server.model.Progress;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.service.CheckService;
import org.smirnowku.hwsc.server.service.ClassroomService;
import org.smirnowku.hwsc.server.service.ProgressService;
import org.smirnowku.hwsc.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private ProgressService progressService;

    @Resource
    private CheckService checkService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/classrooms/teacher")
    public ResponseEntity<List<Classroom>> getClassroomsAsTeacher(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getClassroomsAsTeacher(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/classrooms/student")
    public ResponseEntity<List<Classroom>> getClassroomsAsStudent(@PathVariable long id) {
        return new ResponseEntity<>(classroomService.getClassroomsAsStudent(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/progress/todo")
    public ResponseEntity<List<Progress>> getProgressToDo(@PathVariable long id) {
        return new ResponseEntity<>(progressService.getProgressToDo(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/progress/solved")
    public ResponseEntity<List<Progress>> getProgressSolved(@PathVariable long id) {
        return new ResponseEntity<>(progressService.getProgressSolved(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/progress/completed")
    public ResponseEntity<List<Progress>> getProgressCompleted(@PathVariable long id) {
        return new ResponseEntity<>(progressService.getProgressCompleted(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/check/pending")
    public ResponseEntity<List<Check>> getPending(@PathVariable long id) {
        return new ResponseEntity<>(checkService.getPending(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/check/checked")
    public ResponseEntity<List<Check>> getChecked(@PathVariable long id) {
        return new ResponseEntity<>(checkService.getChecked(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUpDto signUpDto) {
        userService.signUp();
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
