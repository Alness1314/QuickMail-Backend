package com.alness.quickmail.sender.service;

import java.util.Map;

import com.alness.quickmail.sender.dto.ResponseDto;

public interface EmailService {
    public ResponseDto sendSimpleMail(String to, String subject, String body);

    public ResponseDto sendEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
