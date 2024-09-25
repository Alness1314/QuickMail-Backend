package com.alness.quickmail.history.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.quickmail.history.entity.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, UUID>, JpaSpecificationExecutor<HistoryEntity>{
    
}
