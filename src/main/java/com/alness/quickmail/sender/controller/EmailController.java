package com.alness.quickmail.sender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/send-test")
    public ResponseEntity<ResponseDto> getMethodName(@RequestParam String to, @RequestParam String subject,
            @RequestParam String template, @RequestParam Map<String, Object> variables) {
        ResponseDto response = emailService.sendEmail(to, subject, template, variables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
