package com.alness.quickmail.alert.service;

import java.util.List;

import com.alness.quickmail.sender.dto.CertificadeDto;
import com.alness.quickmail.sender.dto.ResponseDto;

public interface AlertSendService {
    public List<CertificadeDto> expiredCertificates(List<CertificadeDto> certs);

    public ResponseDto sendAlert();
}
