package org.smirnowku.hwsc.rest.controller;

import org.smirnowku.hwsc.core.service.HomeworkTemplateService;
import org.smirnowku.hwsc.dto.HomeworkTemplateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/hw-template")
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
    public ResponseEntity<List<HomeworkTemplateDto>> get(@PathVariable String username) {
        return new ResponseEntity<>(service.get(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomeworkTemplateDto> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String username, @PathVariable long id) {
        service.delete(username, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
