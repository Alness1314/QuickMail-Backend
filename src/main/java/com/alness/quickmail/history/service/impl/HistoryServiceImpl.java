package com.alness.quickmail.history.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.quickmail.exceptions.RestExceptionHandler;
import com.alness.quickmail.history.dto.request.HistoryRequest;
import com.alness.quickmail.history.dto.response.HistoryResponse;
import com.alness.quickmail.history.entity.HistoryEntity;
import com.alness.quickmail.history.repository.HistoryRepository;
import com.alness.quickmail.history.service.HistoryService;
import com.alness.quickmail.history.specification.HistorySpecification;
import com.alness.quickmail.utils.CodeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public HistoryResponse findOne(String id) {
        Specification<HistoryEntity> specification = filterWithParams(Map.of("id", id));
        HistoryEntity history = historyRepository.findOne(specification)
                .orElseThrow(() -> new RestExceptionHandler(CodeUtils.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Histroy not found."));
        return mapperHistory(history);
    }

    @Override
    public List<HistoryResponse> find(Map<String, String> params) {
        Specification<HistoryEntity> specification = filterWithParams(params);
        return historyRepository.findAll(specification).stream()
                .map(this::mapperHistory) // Usa un método de referencia para mapear cada entidad
                .collect(Collectors.toList()); // Recoge el resultado en una lista directamente
    }

    @Override
    public HistoryResponse save(HistoryRequest request) {
        HistoryEntity newHistory = mapper.map(request, HistoryEntity.class);
        try {
            newHistory = historyRepository.save(newHistory);
        } catch (Exception e) {
            log.error("Error to save: ", e);
            throw new RestExceptionHandler(CodeUtils.API_CODE_409, HttpStatus.CONFLICT,
                    "History not save.");
        }
        return mapperHistory(newHistory);
    }

    public Specification<HistoryEntity> filterWithParams(Map<String, String> params) {
        return new HistorySpecification().getSpecificationByFilters(params);
    }

    private HistoryResponse mapperHistory(HistoryEntity source) {
        return mapper.map(source, HistoryResponse.class);
    }
}
