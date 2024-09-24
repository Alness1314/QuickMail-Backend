package com.alness.quickmail.sender.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alness.quickmail.nodeknowledge.dto.NodeKnowledgeResp;
import com.alness.quickmail.nodeknowledge.service.NodeKnowledgeService;
import com.alness.quickmail.sender.dto.CertificadeDto;
import com.alness.quickmail.sender.service.CertificadeService;

@Service
public class CertificadeServiceImpl implements CertificadeService{

    @Autowired
    private NodeKnowledgeService nodeKnowledgeService;

    @Override
    public List<CertificadeDto> findCertificades() {
        return nodeKnowledgeService.find(Map.of("articulo", "true")).stream()
        .map(NodeKnowledgeResp::getArticle)
        .filter(Objects::nonNull)
        .flatMap(article -> {
            List<Map<String, Object>> resp = filterCertArticle(article);
            return resp != null ? resp.stream() : Stream.empty();
        })
        .filter(Objects::nonNull)
        .map(mapItem -> new CertificadeDto(
                mapItem.get("name").toString(),
                mapItem.get("fileId").toString(),
                mapItem.get("vigencia").toString()))
        .collect(Collectors.toList());
    }


     @SuppressWarnings("unchecked")
    private List<Map<String, Object>> filterCertArticle(List<Map<String, Object>> data) {
        for (Map<String,Object> map : data) {
            if(map.get("type").equals("Certs")){
                return (List<Map<String, Object>>) map.get("value");
            }
        }
        return Collections.emptyList();
    }
    
}
