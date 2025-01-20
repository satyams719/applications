package com.basics.securitydemo.security;

import com.basics.securitydemo.enums.Permission;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.service.RoleService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final RoleService roleService;

    public CustomPermissionEvaluator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !(permission instanceof String)) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(permission)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }


}
