package com.alness.quickmail.history.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "history")
@Getter
@Setter
public class HistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "file_id", nullable = false, columnDefinition = "uuid")
    private UUID fileId;

    @Column(name = "name", nullable = false, columnDefinition = "character varying")
    private String name;

    @Column(name = "validity", nullable = false, columnDefinition = "character varying")
    private String validity;

    @Column(name = "is_valid", nullable = false, columnDefinition = "boolean")
    private Boolean isValid;

    @Column(name = "send_alert", nullable = false, columnDefinition = "boolean")
    private Boolean sendAlert;

    @Column(name = "date_send_alert", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime dateSendAlert;

    @Column(name = "date_create", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime dateCreate;

    @PrePersist
    public void init() {
        setDateCreate(LocalDateTime.now());
    }

}
