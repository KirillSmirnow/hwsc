package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.dto.HomeworkSolutionDto;
import org.smirnowku.hwsc.service.HomeworkSolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{username}/hw-solution")
@CrossOrigin
public class HomeworkSolutionController {

    @Resource
    private HomeworkSolutionService service;

    @PutMapping("/{id}")
    public ResponseEntity save(@PathVariable String username, @PathVariable long id,
                               @RequestBody HomeworkSolutionDto dto) {
        service.save(username, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
