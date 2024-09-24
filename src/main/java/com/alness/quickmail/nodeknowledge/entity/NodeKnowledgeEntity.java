package com.alness.quickmail.nodeknowledge.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "node_knowledge")
@Getter
@Setter
public class NodeKnowledgeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, columnDefinition = "character varying")
    private String name;

    @Column(name = "tag", nullable = false, columnDefinition = "character varying")
    private String tag;

    @Column(name = "description", nullable = false, columnDefinition = "character varying")
    private String description;

    @Column(name = "article", nullable = false, columnDefinition = "json")
    private String article;
}
