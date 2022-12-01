package com.example.onlineLib.entity;

import com.example.onlineLib.entity.enums.PermissionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<PermissionEnum> permissions;

    public Role(String name) {
        this.name = name;
    }

}
