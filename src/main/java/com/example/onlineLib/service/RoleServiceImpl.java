package com.example.onlineLib.service;

import com.example.onlineLib.entity.Role;
import com.example.onlineLib.entity.User;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.repository.RoleRepository;
import com.example.onlineLib.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Override
    public ApiResult<Boolean> setRole(Long roleId, UUID userId) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> RestException.restThrow("no user found", HttpStatus.NOT_FOUND));

            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> RestException.restThrow("no role found", HttpStatus.NOT_FOUND));

            user.setRole(role);

            userRepository.save(user);

            return ApiResult.successResponse();
    }
}
