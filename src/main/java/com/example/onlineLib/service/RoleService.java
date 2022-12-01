package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;

import java.util.UUID;

public interface RoleService {

    ApiResult<Boolean> setRole(Long roleId, UUID userId);
}
