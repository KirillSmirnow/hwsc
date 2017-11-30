package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.HomeworkTemplate;
import org.smirnowku.hwsc.model.dto.HomeworkTemplateDto;
import org.smirnowku.hwsc.service.HomeworkTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/hw-template")
@CrossOrigin
public class HomeworkTemplateController {

    @Resource
    private HomeworkTemplateService service;

    @PostMapping
    public ResponseEntity create(@PathVariable String username, @RequestBody HomeworkTemplateDto dto) {
        service.create(username, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable String username, @PathVariable long id,
                               @RequestBody HomeworkTemplateDto dto) {
        service.edit(username, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HomeworkTemplate>> get(@PathVariable String username) {
        return new ResponseEntity<>(service.get(username), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String username, @PathVariable long id) {
        service.delete(username, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}