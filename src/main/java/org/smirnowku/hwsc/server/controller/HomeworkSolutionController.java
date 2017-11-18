package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.server.service.HomeworkSolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{userId}/hw-solution")
@CrossOrigin
public class HomeworkSolutionController {

    @Resource
    private HomeworkSolutionService service;

    @PutMapping("/{id}")
    public ResponseEntity save(@PathVariable long userId, @PathVariable long id,
                               @RequestBody HomeworkSolutionDto dto) {
        service.save(userId, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
