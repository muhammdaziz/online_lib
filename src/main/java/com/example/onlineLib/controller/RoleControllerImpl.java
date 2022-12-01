package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController{

    private final RoleService roleService;

    @Override
    public ApiResult<Boolean> setRole(Long roleId, UUID userId) {
        return roleService.setRole(roleId, userId);
    }
}
