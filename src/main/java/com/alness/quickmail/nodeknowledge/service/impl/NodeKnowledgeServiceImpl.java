package com.alness.quickmail.nodeknowledge.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.quickmail.exceptions.RestExceptionHandler;
import com.alness.quickmail.nodeknowledge.dto.NodeKnowledgeResp;
import com.alness.quickmail.nodeknowledge.entity.NodeKnowledgeEntity;
import com.alness.quickmail.nodeknowledge.repository.NodeKnowledgeRepo;
import com.alness.quickmail.nodeknowledge.service.NodeKnowledgeService;
import com.alness.quickmail.nodeknowledge.specification.NodeKnowledgeSpecification;
import com.alness.quickmail.utils.CodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NodeKnowledgeServiceImpl implements NodeKnowledgeService {
    @Autowired
    private NodeKnowledgeRepo nodeKnowledgeRepo;

    private ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.getConfiguration().setFieldMatchingEnabled(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // CONVERTERS
        Converter<String, List<Map<String, Object>>> stringToMap = new AbstractConverter<String, List<Map<String, Object>>>() {
            @Override
            protected List<Map<String, Object>> convert(String source) {
                if (source == null) {
                    return Collections.emptyList();
                } else {
                    return stringJsonToMap(source);
                }
            }
        };

        // PRODUCTO
        mapper.createTypeMap(NodeKnowledgeEntity.class,
                NodeKnowledgeResp.class)
                .addMappings(mapp -> mapp.using(stringToMap).map(NodeKnowledgeEntity::getArticle,
                        NodeKnowledgeResp::setArticle));
    }

    @Override
    public List<NodeKnowledgeResp> find(Map<String, String> params) {
        Specification<NodeKnowledgeEntity> specification = filterWithParameters(params);
        return nodeKnowledgeRepo.findAll(specification).stream()
                .map(this::mapperNode) // Usa un método de referencia para mapear cada entidad
                .collect(Collectors.toList()); // Recoge el resultado en una lista directamente
    }

    @Override
    public NodeKnowledgeResp findOne(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    private NodeKnowledgeResp mapperNode(NodeKnowledgeEntity item) {
        return mapper.map(item, NodeKnowledgeResp.class);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> stringJsonToMap(String jsonArticle) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonArticle, List.class);
        } catch (JsonProcessingException e) {
            log.error("Error procesar json: ", e);
            e.printStackTrace();
            throw new RestExceptionHandler(CodeUtils.API_CODE_409, HttpStatus.CONFLICT,
                    "Error al procesar el json.");
        }
    }

    public Specification<NodeKnowledgeEntity> filterWithParameters(Map<String, String> parameters) {
        return new NodeKnowledgeSpecification().getSpecificationByFilters(parameters);
    }

}
