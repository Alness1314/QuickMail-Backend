package com.alness.quickmail.history.controller;

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

import com.alness.quickmail.history.dto.request.HistoryRequest;
import com.alness.quickmail.history.dto.response.HistoryResponse;
import com.alness.quickmail.history.service.HistoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/history")
@Tag(name = "History", description = ".")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping()
    public ResponseEntity<List<HistoryResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<HistoryResponse> response = historyService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryResponse> findOne(@PathVariable String id) {
        HistoryResponse response = historyService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<HistoryResponse> save(@RequestBody HistoryRequest request) {
        HistoryResponse response = historyService.save(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
