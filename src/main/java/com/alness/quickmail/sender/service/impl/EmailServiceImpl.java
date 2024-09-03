package com.alness.quickmail.sender.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.EmailService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private static final String LOGO_IMAGE_PATH = "classpath:img/logo.png";

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from}")
    private String emailFrom;

    @Autowired
    private ResourceLoader resourceLoader;

    private final Handlebars handlebars = new Handlebars();

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
    public ResponseDto sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            // Cargar la plantilla
            Template template = handlebars.compile(templateName);

            // Aplicar los datos a la plantilla
            String html = template.apply(variables);

            // Crear el mensaje MIME
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(emailFrom);

            // Adjuntar imagen y configurar el cid
            Resource resource = resourceLoader.getResource(LOGO_IMAGE_PATH);
            FileSystemResource logo = new FileSystemResource(resource.getFile());
            helper.addInline("unique@logo.cid", logo);

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

}
