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
    public ResponseEntity create(@PathVariable long userId, @RequestBody HomeworkTemplateDto dto) {
        service.create(userId, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable long userId, @PathVariable long id,
                               @RequestBody HomeworkTemplateDto dto) {
        service.edit(userId, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HomeworkTemplate>> get(@PathVariable long userId) {
        return new ResponseEntity<>(service.get(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long userId, @PathVariable long id) {
        service.delete(userId, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
