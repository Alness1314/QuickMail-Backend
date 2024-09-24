package com.alness.quickmail.users.service.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alness.quickmail.users.dto.UserResponse;
import com.alness.quickmail.users.entity.UserEntity;
import com.alness.quickmail.users.repository.UserRepository;
import com.alness.quickmail.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<UserResponse> find(Map<String, String> params) {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        users.forEach(item -> responses.add(mapperUser(item)));
        return responses;
    }

    @Override
    public UserResponse findOne(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    private UserResponse mapperUser(UserEntity source) {
        return mapper.map(source, UserResponse.class);
    }

}
