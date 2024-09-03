package com.alness.quickmail.sender.service;

import com.alness.quickmail.sender.dto.EmailRequest;
import com.alness.quickmail.sender.dto.ResponseDto;

public interface EmailService {
    public ResponseDto sendSimpleMail(String to, String subject, String body);

    public ResponseDto sendEmail(EmailRequest request);
}
