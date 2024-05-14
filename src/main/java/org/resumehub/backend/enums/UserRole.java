package org.resumehub.backend.enums;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN();

    private final String role;

    UserRole() {
        this.role = "ADMIN";
    }

}
