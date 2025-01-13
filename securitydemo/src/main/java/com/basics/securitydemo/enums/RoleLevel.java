package com.basics.securitydemo.enums;

import lombok.Getter;

@Getter
public enum RoleLevel {

    SUPER_ADMIN(1),
    ADMIN(2),
    MODERATOR(3),
    USER(4),
    GUEST(5);

    private final int level;

    RoleLevel(int level) {
        this.level = level;
    }

}

