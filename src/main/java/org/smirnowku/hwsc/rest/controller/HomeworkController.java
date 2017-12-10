package org.smirnowku.hwsc.rest.controller;

import org.smirnowku.hwsc.core.service.HomeworkService;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{username}/homework")
public class HomeworkController {

    @Resource
    private HomeworkService service;

    @PostMapping
    public ResponseEntity assign(@PathVariable String username, @RequestParam long homeworkTemplateId,
                                 @RequestParam long classroomId, @RequestBody HomeworkDto dto) {
        service.assign(username, homeworkTemplateId, classroomId, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity finish(@PathVariable String username, @PathVariable long id) {
        service.finish(username, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
