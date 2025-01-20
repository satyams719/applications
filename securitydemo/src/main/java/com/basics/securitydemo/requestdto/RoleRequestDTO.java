package com.basics.securitydemo.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDTO {
    private String roleName;
    private String roleLevel;
    private Set<String> rolePermission;
    private String description;
    private boolean isActive;
    private Long parentRoleId;
    private Long childRoleId;

}
