package com.example.onlineLib.payload;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SetRoleDTO {

    private UUID userId;

    private Long roleId;
}
