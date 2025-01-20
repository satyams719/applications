package com.basics.securitydemo.service;

import com.basics.securitydemo.dao.RoleDAO;
import com.basics.securitydemo.enums.Permission;
import com.basics.securitydemo.enums.RoleLevel;
import com.basics.securitydemo.model.Role;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.requestdto.RoleRequestDTO;
import com.basics.securitydemo.responsedto.RoleResponseDTO;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleDAO roleDAO;

    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

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


    public Role createRoleObject(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setName(roleRequestDTO.getRoleName());
        if(roleRequestDTO.getParentRoleId() != null){
            try {
                Role parentRole = roleDAO.getRolesById(roleRequestDTO.getParentRoleId());
                role.setParentRole(parentRole);
            }catch (RoleNotFoundException e){
                //do some operations
            }
        }
//        if(roleRequestDTO.getChildRoleId() != null){
//            Role childRole = roleDAO.getAllChildRoleById(roleRequestDTO.getChildRoleId());
//            role.setChildren(childRole);
//        }
        role.setPermissions(createPermission(roleRequestDTO.getRolePermission()));
        role.setName(roleRequestDTO.getRoleName());
        role.setLevel(RoleLevel.valueOf(roleRequestDTO.getRoleLevel()));
        return role;
    }


    public Set<Permission> createPermission(Set<String> permissions){
        Set<Permission> permissionSet = new HashSet<>();
        for (String permission: permissions){
            permissionSet.add(Permission.valueOf(permission));
        }
        return permissionSet;
    }

    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO){
        Role role = createRoleObject(roleRequestDTO);
        Role savedRole = roleDAO.saveRole(role);
        return createRoleResponseDTO(savedRole);
    }

    public RoleResponseDTO createRoleResponseDTO(Role role){
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setRole_name(role.getName());
        roleResponseDTO.setRole_id(role.getId());
        return new RoleResponseDTO();
    }


}
