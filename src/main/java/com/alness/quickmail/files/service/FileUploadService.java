package com.alness.quickmail.files.service;

import com.alness.quickmail.files.dto.FileUploadRequest;
import com.alness.quickmail.sender.dto.ResponseDto;

public interface FileUploadService {
    public ResponseDto saveFile(FileUploadRequest request);
}
