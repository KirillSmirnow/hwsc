package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.controller.dto.HomeworkDto;
import org.smirnowku.hwsc.server.model.HomeworkSolution;
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
    private HomeworkService homeworkService;

    @GetMapping("/{homeworkId}")
    public ResponseEntity<HomeworkSolution> getHomeworkSolution(@PathVariable long userId, @PathVariable long homeworkId) {
        return new ResponseEntity<>(homeworkService.getHomeworkSolution(userId, homeworkId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createHomework(@RequestParam long classroomId, @RequestBody HomeworkDto homeworkDto) {
        homeworkService.createHomework(classroomId, homeworkDto.name, homeworkDto.deadline);
        return new ResponseEntity(HttpStatus.OK);
    }
}
