package org.smirnowku.hwsc.rest.controller;

import org.smirnowku.hwsc.core.service.impl.CheckService;
import org.smirnowku.hwsc.dto.AssignmentDto;
import org.smirnowku.hwsc.dto.CheckDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/check")
public class CheckController {

    @Resource
    private CheckService service;

    @PutMapping("/{id}")
    public ResponseEntity submit(@PathVariable String username, @PathVariable long id, @RequestBody AssignmentDto dto) {
        service.submit(username, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CheckDto>> getPending(@PathVariable String username) {
        return new ResponseEntity<>(service.getPending(username), HttpStatus.OK);
    }

    @GetMapping("/checked")
    public ResponseEntity<List<CheckDto>> getChecked(@PathVariable String username) {
        return new ResponseEntity<>(service.getChecked(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckDto> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }
}
