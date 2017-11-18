package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.HomeworkSolution;
import org.smirnowku.hwsc.server.service.CheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/{userId}/check/{progressId}")
@CrossOrigin
public class CheckController {

    @Resource
    private CheckService checkService;

    @GetMapping
    public ResponseEntity<HomeworkSolution> getHomeworkSolutionToCheck(@PathVariable long progressId) {
        return new ResponseEntity<>(checkService.getHomeworkSolutionToCheck(progressId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity submitCheck(@PathVariable long progressId, @RequestBody ResultDto resultDto) {
        checkService.submitCheck(progressId, resultDto.result);
        return new ResponseEntity(HttpStatus.OK);
    }
}
