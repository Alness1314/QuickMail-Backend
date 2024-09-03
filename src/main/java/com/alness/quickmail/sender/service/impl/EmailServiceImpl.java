package com.alness.quickmail.sender.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.quickmail.sender.dto.AttachmentsDto;
import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;
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

    String separator = "file.separator";

    private final String templatePath = System.getProperty(separator) + "quickmail" + System.getProperty(separator)
            + "templates"
            + System.getProperty(separator);

    private final String imgPath = System.getProperty(separator) + "quickmail" + System.getProperty(separator)
            + "img"
            + System.getProperty(separator);

    @PostConstruct
    public void init() {
        File dir = new File(templatePath);
        File dir2 = new File(imgPath);
        if (!dir.exists() || !dir2.exists()) {
            dir.mkdirs();
            dir2.mkdirs();
        }
    }

    @Override
    public ResponseDto sendSimpleMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(emailFrom); // Cambia a tu dirección de correo
            mailSender.send(message);
            return new ResponseDto("Correo enviado.", HttpStatus.ACCEPTED, true);
        } catch (Exception e) {
            log.error("error al enviar el correo ", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Error al enviar el correo: [%s]", e.getMessage()));
        }
    }

    @Override
    public ResponseDto sendEmail(EmailRequest request) {
        try {

            // Cargar la plantilla
            Template template = handlebars.compileInline(loadTemplate(request.getTemplateName()));

            // Aplicar los datos a la plantilla
            if(request.getVariables() == null && request.getVariables().isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Las variables no pueden estar vacias.");
            }
            String html = template.apply(request.getVariables());

            // Crear el mensaje MIME
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);
            helper.setFrom(emailFrom);

            if(request.getImagenes() != null && !request.getImagenes().isEmpty()){
                request.getImagenes().forEach((key, value) -> {
                    log.info("key value: {}", key);
                    try {
                        helper.addInline(value.getUniqueCID(), loadResourse(value.getNameResource()));
                    } catch (MessagingException e) {
                        log.error("Error al cargar el recurso", e);
                    }
                });
            }

            log.debug("size adjuntos: ", request.getAttachments().size());

            if(request.getAttachments() != null && !request.getAttachments().isEmpty()){
                log.debug("Adjuntos");
                for (AttachmentsDto item : request.getAttachments()) {
                    log.debug("Adjuntos name: ",item.getName());
                    helper.addAttachment(item.getName() + item.getMediaType(), getAttachment(item.getBase64()));
                }
            }

            // Enviar el correo electrónico
            mailSender.send(message);

            return new ResponseDto("Correo enviado.", HttpStatus.ACCEPTED, true);
        } catch (Exception e) {
            log.error("error al enviar el correo ", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Error al enviar el correo: [%s]", e.getMessage()));
        }

    }

    private String loadTemplate(String templateName) {
        log.info("template name:  {}", templateName);
        try {
            // validar que tenga extencion
            if (templateName.contains(".")) {
                log.info("tiene extension: {}", templateName);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no debe contener extencion.");
            }
            // Cargar la plantilla desde la nueva ruta
            File templateFile = new File(templatePath + templateName + ".hbs");
            if (!templateFile.exists()) {
                throw new FileNotFoundException("Template file not found: " + templateFile.getAbsolutePath());
            }
            return new String(Files.readAllBytes(templateFile.toPath()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to load Template file: " + e.getMessage());
        }

    }

    private FileSystemResource loadResourse(String imageName) {
        try {
            // validar que tenga extencion
            if (imageName.contains(".")) {
                log.info("tiene extension: {}", imageName);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no debe contener extencion.");
            }
            // Cargar la plantilla desde la nueva ruta
            File imageFile = new File(imgPath + imageName + ".png");

            // Adjuntar imagen y configurar el cid
            return new FileSystemResource(imageFile);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to load Image file: " + e.getMessage());
        }

    }

    
    private InputStreamSource getAttachment(String base64String){
        byte[] resource = Base64.getDecoder().decode(base64String);
        return new ByteArrayResource(resource);
    }

}
