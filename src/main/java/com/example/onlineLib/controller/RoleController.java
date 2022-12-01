package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequestMapping("/api/role")
public interface RoleController {

    @PreAuthorize(value = "hasAnyAuthority('SET_ROLE_TO_USER')" )
    @PostMapping("/set-role/{user-id}")
    ApiResult<Boolean> setRole(@NotNull(message = "role Id must be not null") @RequestBody Long roleId, @PathVariable @NotNull(message = "User Id must be not null") UUID id);
}
