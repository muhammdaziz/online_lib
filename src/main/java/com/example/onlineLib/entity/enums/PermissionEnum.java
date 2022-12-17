package com.example.onlineLib.entity.enums;


import org.springframework.security.core.GrantedAuthority;

public enum PermissionEnum implements GrantedAuthority {

    ADD_BOOK,
    EDIT_BOOK,
    GET_BOOK,
    DELETE_BOOK,
    ADD_CATEGORY,
    EDIT_CATEGORY,
    GET_CATEGORY,
    DELETE_CATEGORY,
    ADD_AUTHOR,
    EDIT_AUTHOR,
    GET_AUTHOR,
    DELETE_AUTHOR,
    ADD_POPULAR_BOOK,
    EDIT_POPULAR_BOOK,
    GET_POPULAR_BOOK,
    DELETE_POPULAR_BOOK,

    SET_ROLE_TO_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
