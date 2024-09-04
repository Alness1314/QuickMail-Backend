package com.alness.quickmail.sender.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.alness.quickmail.exceptions.RestExceptionHandler;
import com.alness.quickmail.sender.dto.AttachmentsDto;
import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;
import com.alness.quickmail.utils.CodeUtils;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String emailFrom;

    private final Handlebars handlebars = new Handlebars();

    private final String baseDir = System.getProperty("user.dir") + File.separator + "assets" + File.separator;
    private final String templatePath = baseDir + "templates" + File.separator;
    private final String imgPath = baseDir + "img" + File.separator;

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
    public ResponseDto sendEmail(EmailRequest request) {
        try {

            // Cargar la plantilla
            Template template = handlebars.compileInline(loadTemplate(request.getTemplateName()));

            // Aplicar los datos a la plantilla
            String html = template.apply(request.getVariables());

            // Crear el mensaje MIME
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);
            helper.setFrom(emailFrom);

            if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
                request.getImagenes().forEach((key, value) -> {
                    try {
                        helper.addInline(value.getUniqueCID(), loadResourse(value.getNameResource()));
                    } catch (MessagingException e) {
                        log.error("Error al cargar el recurso", e);
                    }
                });
            }

            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                for (AttachmentsDto item : request.getAttachments()) {
                    helper.addAttachment(item.getName() + item.getMediaType(), getAttachment(item.getBase64()));
                }
            }

            // Enviar el correo electrónico
            mailSender.send(message);

            return new ResponseDto("Correo enviado.", HttpStatus.ACCEPTED, true);
        } catch (RestExceptionHandler e) {
            // Manejar excepciones personalizadas y lanzar el error tal cual
            log.error("Error específico al enviar el correo: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Manejar cualquier otra excepción desconocida
            log.error("Error al enviar el correo", e);
            throw new RestExceptionHandler(CodeUtils.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al enviar el correo revise los registros del sistema.");
        }

    }

    private String loadTemplate(String templateName) {
        // validar que tenga extencion
        if (templateName.contains(".")) {
            throw new RestExceptionHandler(CodeUtils.API_CODE_400, HttpStatus.BAD_REQUEST,
                    "El nombre no debe contener extencion.");
        }

        try {
            // Cargar la plantilla desde la nueva ruta
            File templateFile = new File(templatePath + templateName + ".hbs");
            if (!templateFile.exists()) {
                throw new RestExceptionHandler(CodeUtils.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Archivo plantilla no encontrado.");
            }
            return new String(Files.readAllBytes(templateFile.toPath()));
        } catch (RestExceptionHandler e) {
            // Rethrow RestExceptionHandler tal cual sin modificar
            throw e;
        } catch (IOException e) {
            // Si ocurre un problema relacionado con I/O, lanzar error 500 específico
            throw new RestExceptionHandler(CodeUtils.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al leer el archivo plantilla.");
        } catch (Exception e) {
            // Capturar cualquier otro tipo de excepción no específica
            throw new RestExceptionHandler(CodeUtils.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error desconocido al cargar el archivo plantilla.");
        }

    }

    private FileSystemResource loadResourse(String imageName) {
        try {
            // validar que tenga extencion
            if (imageName.contains(".")) {
                throw new RestExceptionHandler(CodeUtils.API_CODE_400, HttpStatus.BAD_REQUEST,
                        "El nombre no debe contener extencion.");
            }
            // Cargar la plantilla desde la nueva ruta
            File imageFile = new File(imgPath + imageName + ".png");
            if (!imageFile.exists()) {
                throw new RestExceptionHandler(CodeUtils.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Archivo de imagen no encontrado.");
            }

            // Adjuntar imagen y configurar el cid
            return new FileSystemResource(imageFile);
        } catch (RestExceptionHandler e) {
            // Rethrow RestExceptionHandler tal cual sin modificar
            throw e;
        } catch (Exception e) {
            throw new RestExceptionHandler(CodeUtils.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al cargar el archivo imagen.");
        }

    }

    private InputStreamSource getAttachment(String base64String) {
        byte[] resource = Base64.getDecoder().decode(base64String);
        return new ByteArrayResource(resource);
    }

}
