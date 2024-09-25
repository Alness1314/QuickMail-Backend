package com.alness.quickmail.history.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class HistoryRequest {
    private UUID fileId;
    private String name;
    private String validity;
    private Boolean isValid;
    private Boolean sendAlert;
    private LocalDateTime dateSendAlert;
}
