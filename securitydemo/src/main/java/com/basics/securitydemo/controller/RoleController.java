package com.basics.securitydemo.controller;

import com.basics.securitydemo.requestdto.RoleRequestDTO;
import com.basics.securitydemo.responsedto.RoleResponseDTO;
import com.basics.securitydemo.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class RoleController {
    private final RoleService roleService;

    RoleController(RoleService roleService){
        this.roleService = roleService;
    }


    @PostMapping("create/role")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequest){
        RoleResponseDTO roleResponseDTO  =roleService.createRole(roleRequest);
        return new ResponseEntity<>(roleResponseDTO, HttpStatus.CREATED);
    }

}
