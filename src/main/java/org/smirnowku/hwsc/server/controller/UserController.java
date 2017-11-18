package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.UserDto;
import org.smirnowku.hwsc.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService service;

    @PostMapping
    public ResponseEntity signUp(@RequestBody UserDto dto) {
        service.signUp(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser(@PathVariable long id, @RequestBody UserDto dto) {
        //
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return new ResponseEntity<>(service.getUser(id), HttpStatus.OK);
    }
}
