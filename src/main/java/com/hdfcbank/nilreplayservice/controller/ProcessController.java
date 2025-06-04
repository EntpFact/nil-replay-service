package com.hdfcbank.nilreplayservice.controller;

import com.hdfcbank.nilreplayservice.service.ReplayService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private ReplayService replayService;

    @GetMapping("/replay/failed")
    public ResponseEntity<String> replayFailedEvents() {
        replayService.replayFailedMessages();
        return ResponseEntity.ok("Failed events replay process is Success");
    }

    @GetMapping("/replay/received")
    public ResponseEntity<String> replayReceivedEvents() {
        replayService.replayReceivedMessages();
        return ResponseEntity.ok("Received events replay process is Success");
    }
}
