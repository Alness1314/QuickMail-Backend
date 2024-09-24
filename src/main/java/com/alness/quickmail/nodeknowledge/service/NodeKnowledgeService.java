package com.alness.quickmail.nodeknowledge.service;

import java.util.List;
import java.util.Map;

import com.alness.quickmail.nodeknowledge.dto.NodeKnowledgeResp;

public interface NodeKnowledgeService {
    public List<NodeKnowledgeResp> find(Map<String, String> params);
    public NodeKnowledgeResp findOne(String id);
}
