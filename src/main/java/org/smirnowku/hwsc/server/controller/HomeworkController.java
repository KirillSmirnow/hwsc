package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.dto.HomeworkDto;
import org.smirnowku.hwsc.server.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{userId}/homework")
@CrossOrigin
public class HomeworkController {

    @Resource
    private HomeworkService service;

    @PostMapping
    public ResponseEntity assignHomework(@PathVariable long userId, @RequestParam long classroomId,
                                         @RequestBody HomeworkDto dto) {
        //
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
