package com.alness.quickmail.history.service;

import java.util.List;
import java.util.Map;

import com.alness.quickmail.history.dto.request.HistoryRequest;
import com.alness.quickmail.history.dto.response.HistoryResponse;

public interface HistoryService {
    public HistoryResponse findOne(String id);
    public List<HistoryResponse> find(Map<String, String> params);
    public HistoryResponse save(HistoryRequest request);
}
