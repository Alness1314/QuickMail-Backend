package com.alness.quickmail.sender.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.sender.dto.CertificadeDto;
import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.CertificadeService;
import com.alness.quickmail.sender.service.EmailService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("${api.prefix}/helper")
@Tag(name = "E-Mail", description = ".")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private CertificadeService certificados;

    @PostMapping("/send-email")
    public ResponseEntity<ResponseDto> getMethodName(@Valid @RequestBody EmailRequest request) {
        ResponseDto response = emailService.sendEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/test")
    public ResponseEntity<List<CertificadeDto>> getMethodName() {
        List<CertificadeDto> response = certificados.findCertificades();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

}
