package com.basics.securitydemo.controller;

import com.basics.securitydemo.requestdto.LoginRequest;
import com.basics.securitydemo.requestdto.RegisterUserRequestDTO;
import com.basics.securitydemo.requestdto.UpdatePasswordRequestDTO;
import com.basics.securitydemo.responsedto.LoginResponse;
import com.basics.securitydemo.responsedto.RegisterUserResponseDTO;
import com.basics.securitydemo.service.UserService;
import com.basics.securitydemo.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("register/")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) throws Exception {
            return new ResponseEntity<>(userService.registerUser(registerUserRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("update/password/")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO){
        return new ResponseEntity<>(userService.updateUserPassword(updatePasswordRequestDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping("login/")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse(jwtToken, userDetails.getUsername(), roles);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
