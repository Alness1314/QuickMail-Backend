package com.alness.quickmail.sender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/helper")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public ResponseEntity<ResponseDto> sendMail(@RequestParam String to, @RequestParam String subject,
            @RequestParam String text) {
        ResponseDto response = emailService.sendSimpleMail(to, subject, text);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send-test")
    public ResponseEntity<ResponseDto> getMethodName(@RequestBody EmailRequest request) {
        ResponseDto response = emailService.sendEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
