package com.alness.quickmail.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.alert.service.AlertSendService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("${api.prefix}/alerts-test")
@Tag(name = "Alerts", description = ".")
public class AlertSendController {
    @Autowired
    private AlertSendService alertSendService;

    @GetMapping()
    public ResponseEntity<Boolean> getMethodName() {
        alertSendService.sendAlert();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
