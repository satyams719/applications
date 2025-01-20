package com.basics.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class TestController {

    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication, null, 'CREATE_USER')")
    @GetMapping("test/")
    public String test(){
        return "Application is OK";
    }

    @GetMapping("/debug")
    public String debug(Authentication authentication) {
        System.out.println("Authentication: " + authentication);
        return "Debug endpoint reached.";
    }

}
