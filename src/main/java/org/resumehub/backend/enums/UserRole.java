package org.resumehub.backend.enums;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
