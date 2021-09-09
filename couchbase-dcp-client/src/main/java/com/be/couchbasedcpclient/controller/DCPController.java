package com.be.couchbasedcpclient.controller;

import com.be.couchbasedcpclient.service.DCPService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dcp")
public class DCPController {

    private final DCPService dcpService;

    public DCPController(DCPService dcpService) {
        this.dcpService = dcpService;
    }

    @PostMapping("/start-loop")
    @ResponseStatus(value = HttpStatus.OK)
    public void startLoop() throws InterruptedException {
        dcpService.start(true);
    }

    @GetMapping("/get")
    public void get() throws InterruptedException {
        dcpService.start(false);
    }
}
