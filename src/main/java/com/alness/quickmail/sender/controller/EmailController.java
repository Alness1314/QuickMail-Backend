package com.alness.quickmail.sender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/helper")
@Tag(name = "E-Mail", description = ".")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<ResponseDto> getMethodName(@Valid @RequestBody EmailRequest request) {
        ResponseDto response = emailService.sendEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
