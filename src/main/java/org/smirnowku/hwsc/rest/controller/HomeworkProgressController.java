package org.smirnowku.hwsc.rest.controller;

import org.smirnowku.hwsc.core.service.HomeworkProgressService;
import org.smirnowku.hwsc.dto.HomeworkProgressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/hw-progress")
public class HomeworkProgressController {

    @Resource
    private HomeworkProgressService service;

    @GetMapping("/{homeworkId}")
    public ResponseEntity<List<HomeworkProgressDto>> get(@PathVariable long homeworkId) {
        return new ResponseEntity<>(service.get(homeworkId), HttpStatus.OK);
    }
}
