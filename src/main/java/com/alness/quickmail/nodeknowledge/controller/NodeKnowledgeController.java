package com.alness.quickmail.nodeknowledge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alness.quickmail.nodeknowledge.dto.NodeKnowledgeResp;
import com.alness.quickmail.nodeknowledge.service.NodeKnowledgeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("${api.prefix}/node-knowledge")
@RestController
@Tag(name = "Node Knowledge", description = ".")
public class NodeKnowledgeController {
    @Autowired
    private NodeKnowledgeService nodeKnowledgeService;

    @GetMapping()
    public ResponseEntity<List<NodeKnowledgeResp>> findAll(@RequestParam Map<String, String> parameters) {
        List<NodeKnowledgeResp> response = nodeKnowledgeService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeKnowledgeResp> findOne(@PathVariable String id) {
        NodeKnowledgeResp response = nodeKnowledgeService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
