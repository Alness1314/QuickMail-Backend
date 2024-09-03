package com.alness.quickmail.sender.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private List<AttachmentsDto> attachments;
    private String templateName;
    private Map<String, String> variables;
    private Map<String, ImageDto> imagenes;    
}
