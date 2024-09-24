package com.alness.quickmail.sender.dto;

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
public class CertificadeDto {
    private String name;
    private String fileId;
    private String vigencia;
}
