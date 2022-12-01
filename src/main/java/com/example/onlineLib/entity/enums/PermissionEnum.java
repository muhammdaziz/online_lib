package com.example.onlineLib.entity.enums;


import org.springframework.security.core.GrantedAuthority;

public enum PermissionEnum implements GrantedAuthority {

    ADD_PRODUCT,
    EDIT_PRODUCT,
    GET_PRODUCT,
    DELETE_PRODUCT,

    ADD_CATEGORY,
    EDIT_CATEGORY,
    GET_CATEGORY,
    DELETE_CATEGORY,

    SET_ROLE_TO_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
