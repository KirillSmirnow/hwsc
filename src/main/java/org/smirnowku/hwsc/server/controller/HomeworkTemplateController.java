package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.HomeworkTemplate;
import org.smirnowku.hwsc.server.model.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.server.service.HomeworkTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{userId}/hw-template")
@CrossOrigin
public class HomeworkTemplateController {

    @Resource
    private HomeworkTemplateService service;

    @PostMapping
    public ResponseEntity createTemplate(@PathVariable long userId, @RequestBody HomeworkTemplateDto dto) {
        //
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity editTemplate(@PathVariable long userId, @PathVariable long id,
                                       @RequestBody HomeworkTemplateDto dto) {
        //
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HomeworkTemplate>> getHomeworkTemplates(@PathVariable long userId) {
        //
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTemplate(@PathVariable long userId, @PathVariable long id) {
        //
        return new ResponseEntity(HttpStatus.OK);
    }
}
