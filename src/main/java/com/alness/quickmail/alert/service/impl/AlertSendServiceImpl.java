package com.alness.quickmail.alert.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alness.quickmail.alert.service.AlertSendService;
import com.alness.quickmail.history.dto.request.HistoryRequest;
import com.alness.quickmail.history.dto.response.HistoryResponse;
import com.alness.quickmail.history.service.HistoryService;
import com.alness.quickmail.sender.dto.CertificadeDto;
import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;
import com.alness.quickmail.sender.service.CertificadeService;
import com.alness.quickmail.sender.service.EmailService;
import com.alness.quickmail.users.dto.UserResponse;
import com.alness.quickmail.users.service.UserService;
import com.alness.quickmail.utils.DateTimeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlertSendServiceImpl implements AlertSendService {
    @Autowired
    private CertificadeService certificadeService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryService historyService;

    @Override
    public List<CertificadeDto> expiredCertificates(List<CertificadeDto> certs) {
        List<CertificadeDto> expired = new ArrayList<>();
        certs.forEach(item -> {
            String dateItem = DateTimeUtils.stringDateNestToDate(item.getVigencia());
            if (!DateTimeUtils.esFechaVigente(dateItem)) {
                expired.add(item);
            }
        });

        return expired;
    }

    @Override
    public ResponseDto sendAlert() {
        // obtenemos todos los certificados
        List<CertificadeDto> certs = certificadeService.findCertificades();

        // obtener la lista de los vencidos
        List<CertificadeDto> expiredCerts = expiredCertificates(certs);

        // buscamos los usuarios a mandar el correo
        List<UserResponse> users = userService.find(Map.of("expiration", "true"));

        // recorremos los usuarios para obtener el correo
        users.forEach(user ->
            // recorremos los certificados vencidos
            expiredCerts.forEach(cert -> {
                // verificamos si existe un registro de ese certificado
                if (Boolean.FALSE.equals(recordExists(cert))) {
                    // enviamos un correo por certificado a cada usuario
                    Boolean response = sendCertificade(user, cert);
                    if (Boolean.TRUE.equals(response)) {
                        HistoryRequest historyReq = HistoryRequest.builder()
                                .fileId(UUID.fromString(cert.getFileId()))
                                .name(cert.getName())
                                .validity(cert.getVigencia())
                                .isValid(false)
                                .sendAlert(response)
                                .dateSendAlert(LocalDateTime.now())
                                .build();
                        historyService.save(historyReq);
                    }
                }
            })
        );

        return null;
    }

    private Boolean sendCertificade(UserResponse user, CertificadeDto cert) {
        try {
            Map<String, Object> variables = Map.of("fullName", user.getFullName(), "nombreCertificado", cert.getName(),
                    "fecha", cert.getVigencia());

            EmailRequest request = EmailRequest.builder()
                    .to(user.getEmail())
                    .subject("Certificado Expirado")
                    .templateName("vigencia-certificado")
                    .variables(variables)
                    .build();

            emailService.sendEmail(request);
            return true;
        } catch (Exception e) {
            log.error("Error to send alert: ", e);
            return false;
        }

    }

    private Boolean recordExists(CertificadeDto cert) {
        List<HistoryResponse> response = historyService.find(Map.of("file", cert.getFileId()));
        if (!response.isEmpty() && response.size() == 1) {
            response.get(0);
            return true;
        } else {
            return false;
        }
    }

}
