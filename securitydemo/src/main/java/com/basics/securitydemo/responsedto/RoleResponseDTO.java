package com.basics.securitydemo.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleResponseDTO {

    Long role_id;
    String role_name;
}
