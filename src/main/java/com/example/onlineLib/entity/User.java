package com.example.onlineLib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        accountNonExpired = accountNonLocked = credentialsNonExpired = true;
        enabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.isNull(role))
            return new ArrayList<>();
        return role.getPermissions();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
