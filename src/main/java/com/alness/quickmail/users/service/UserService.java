package com.alness.quickmail.users.service;

import java.util.Map;
import java.util.List;

import com.alness.quickmail.users.dto.UserResponse;

public interface UserService {
    public List<UserResponse> find(Map<String, String> params);
    public UserResponse findOne(String id);
}
