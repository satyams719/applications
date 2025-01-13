package com.basics.securitydemo.security;

import com.basics.securitydemo.enums.Permission;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.service.RoleService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final RoleService roleService;

    public CustomPermissionEvaluator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        User user = (User) authentication.getPrincipal();
        Set<Permission> permissions = roleService.resolvePermissions(user.getRole());
        return permissions.contains(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
