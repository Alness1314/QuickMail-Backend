package com.alness.quickmail.files.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.quickmail.exceptions.RestExceptionHandler;
import com.alness.quickmail.files.dto.FileUploadRequest;
import com.alness.quickmail.files.service.FileUploadService;
import com.alness.quickmail.sender.dto.ResponseDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    private final String baseDir = System.getProperty("user.dir") + File.separator + "assets" + File.separator;
    private final String templatePath = baseDir + "templates" + File.separator;
    private final String imgPath = baseDir + "img" + File.separator;

    private String codeApi = "P-400";

    @PostConstruct
    public void init() {
        File templateDir = new File(templatePath);
        File imgDir = new File(imgPath);
        if (!templateDir.exists()) {
            templateDir.mkdirs();
        }
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }
    }

    @Override
    public ResponseDto saveFile(FileUploadRequest request) {
        if (request.getFile() == null) {
            throw new RestExceptionHandler(codeApi, HttpStatus.BAD_REQUEST, "El archivo no puede estar vacio.");
        }

        String originalFilename = request.getFile().getOriginalFilename();
        if (originalFilename == null) {
            throw new RestExceptionHandler(codeApi, HttpStatus.BAD_REQUEST, "El archivo debe tener un nombre y extension.");
        }

        // Validar la extensión del archivo
        if (Boolean.TRUE.equals(request.getTemplate()) && !originalFilename.endsWith(".hbs")) {
            throw new RestExceptionHandler(codeApi, HttpStatus.BAD_REQUEST, "Solo se permiten archivos .hbs cuando es un template.");
        }

        if (Boolean.FALSE.equals(request.getTemplate()) && !originalFilename.endsWith(".png")) {
            throw new RestExceptionHandler(codeApi, HttpStatus.BAD_REQUEST, "Solo se permiten archivos .png cuando template es falso.");
        }

        try {
            Path uploadPath = Boolean.TRUE.equals(request.getTemplate()) ? Paths.get(templatePath) : Paths.get(imgPath);
            Path filePath = uploadPath.resolve(request.getFile().getOriginalFilename());
            request.getFile().transferTo(filePath.toFile());

            return new ResponseDto("Archivo cargado con exito.", HttpStatus.ACCEPTED, true);
        } catch (Exception e) {
            log.error("Error al intentar cargar el archivo ", e);
            throw new RestExceptionHandler("P-500", HttpStatus.INTERNAL_SERVER_ERROR, "Error al intentar cargar el archivo");
        }

    }

}
