package org.smirnowku.hwsc.controller;

import org.smirnowku.hwsc.model.Check;
import org.smirnowku.hwsc.model.dto.CheckResultDto;
import org.smirnowku.hwsc.service.CheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/{username}/check")
@CrossOrigin
public class CheckController {

    @Resource
    private CheckService service;

    @PutMapping("/{id}")
    public ResponseEntity submit(@PathVariable String username, @PathVariable long id, @RequestBody CheckResultDto dto) {
        service.submit(username, id, dto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Check>> getPending(@PathVariable String username) {
        return new ResponseEntity<>(service.getPending(username), HttpStatus.OK);
    }

    @GetMapping("/checked")
    public ResponseEntity<List<Check>> getChecked(@PathVariable String username) {
        return new ResponseEntity<>(service.getChecked(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Check> get(@PathVariable String username, @PathVariable long id) {
        return new ResponseEntity<>(service.get(username, id), HttpStatus.OK);
    }
}