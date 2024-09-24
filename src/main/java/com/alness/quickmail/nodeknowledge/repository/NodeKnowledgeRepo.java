package com.alness.quickmail.nodeknowledge.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.quickmail.nodeknowledge.entity.NodeKnowledgeEntity;

public interface NodeKnowledgeRepo extends JpaRepository<NodeKnowledgeEntity, UUID>, JpaSpecificationExecutor<NodeKnowledgeEntity>{
    
}
