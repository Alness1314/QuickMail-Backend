package com.alness.quickmail.users.service.impl;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alness.quickmail.users.dto.UserResponse;
import com.alness.quickmail.users.entity.UserEntity;
import com.alness.quickmail.users.repository.UserRepository;
import com.alness.quickmail.users.service.UserService;
import com.alness.quickmail.users.specification.UserSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<UserResponse> find(Map<String, String> params) {
        Specification<UserEntity> specification = filterWithParameters(params);
        return userRepository.findAll(specification).stream()
                .map(this::mapperUser) // Usa un método de referencia para mapear cada entidad
                .collect(Collectors.toList()); // Recoge el resultado en una lista directamente
    }

    @Override
    public UserResponse findOne(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    private UserResponse mapperUser(UserEntity source) {
        return mapper.map(source, UserResponse.class);
    }

    public Specification<UserEntity> filterWithParameters(Map<String, String> parameters) {
        return new UserSpecification().getSpecificationByFilters(parameters);
    }

}
