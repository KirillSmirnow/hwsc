package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.dto.HomeworkDto;
import org.smirnowku.hwsc.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{username}/homework")
@CrossOrigin
public class HomeworkController {

    @Resource
    private HomeworkService service;

    @PostMapping
    public ResponseEntity assign(@PathVariable String username, @RequestParam long homeworkTemplateId,
                                 @RequestParam long classroomId, @RequestBody HomeworkDto dto) {
        service.assign(username, homeworkTemplateId, classroomId, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
