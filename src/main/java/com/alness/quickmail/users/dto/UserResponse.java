package com.alness.quickmail.users.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private String rol;
    private String codeRecover;
    private OffsetDateTime dateCreatedCodeRecover;
    private Boolean sendExpirationAlert;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
