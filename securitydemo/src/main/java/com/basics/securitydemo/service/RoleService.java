package com.basics.securitydemo.service;

import com.basics.securitydemo.enums.Permission;
import com.basics.securitydemo.model.Role;
import com.basics.securitydemo.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    public Set<Permission> resolvePermissions(Role role) {
        Set<Permission> permissions = new HashSet<>(role.getPermissions());
        Role parent = role.getParentRole();
        while (parent != null) {
            permissions.addAll(parent.getPermissions());
            parent = parent.getParentRole();
        }
        return permissions;
    }

    public boolean canAssign(Role assigner, Role assignee) {
        return assigner.getLevel().getLevel() < assignee.getLevel().getLevel();
    }

    public boolean hasAccess(User user, int roleLevel){
        return user.getRole().getLevel().getLevel() >= roleLevel;
    }
}
