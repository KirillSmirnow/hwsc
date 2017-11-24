package org.smirnowku.hwsc.server.controller;

import org.smirnowku.hwsc.server.model.Check;
import org.smirnowku.hwsc.server.service.CheckService;
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

    @GetMapping("/pending")
    public ResponseEntity<List<Check>> getPending(@PathVariable String username) {
        return new ResponseEntity<>(service.getPending(username), HttpStatus.OK);
    }

    @GetMapping("/checked")
    public ResponseEntity<List<Check>> getChecked(@PathVariable String username) {
        return new ResponseEntity<>(service.getChecked(username), HttpStatus.OK);
    }
}
