package com.alness.quickmail.files.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.files.dto.FileUploadRequest;
import com.alness.quickmail.files.service.FileUploadService;
import com.alness.quickmail.sender.dto.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("${api.prefix}/file")
@Tag(name = "Archivos", description = ".")
public class FileUploadController {
    @Autowired
    private FileUploadService uploadService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@ModelAttribute FileUploadRequest request) {
        ResponseDto response = uploadService.saveFile(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
