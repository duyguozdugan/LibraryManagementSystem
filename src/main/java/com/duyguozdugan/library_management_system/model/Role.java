package com.duyguozdugan.library_management_system.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.duyguozdugan.library_management_system.model.Permission.*;


public enum Role {
    ADMIN(

           Set.of(
                   ADMIN_READ,
                   ADMIN_UPDATE,
                   ADMIN_CREATE,
                   ADMIN_DELETE
           )
    ),
    USER(
            Set.of(
                    USER_READ,
                    USER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
