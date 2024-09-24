package com.alness.quickmail.users.entity;

import java.util.UUID;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "[user]")
@Getter
@Setter
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "\"fullName\"", nullable = false, columnDefinition = "character varying")
    private String fullName;

    @Column(name = "email", nullable = false, columnDefinition = "character varying")
    private String email;

    @Column(name = "\"sendExpirationAlert\"", nullable = true, columnDefinition = "boolean")
    private Boolean sendExpirationAlert;

}
