package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.User;
import org.smirnowku.hwsc.model.dto.UserDto;
import org.smirnowku.hwsc.service.UserService;
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

    @PutMapping("/{username}")
    public ResponseEntity edit(@PathVariable String username, @RequestBody UserDto dto) {
        service.edit(username, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> get(@PathVariable String username) {
        return new ResponseEntity<>(service.get(username), HttpStatus.OK);
    }
}
