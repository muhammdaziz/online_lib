package com.example.onlineLib.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserDTO {

    private UUID id;

    private String email;

    private Collection<? extends GrantedAuthority> permissions;

}
