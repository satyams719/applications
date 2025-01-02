package com.basics.securitydemo.controller;

import com.basics.securitydemo.requestdto.RegisterUserRequestDTO;
import com.basics.securitydemo.requestdto.UpdatePasswordRequestDTO;
import com.basics.securitydemo.responsedto.RegisterUserResponseDTO;
import com.basics.securitydemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("register/")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) throws Exception {
            return new ResponseEntity<>(userService.registerUser(registerUserRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("update/password/")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO){
        return new ResponseEntity<>(userService.updateUserPassword(updatePasswordRequestDTO), HttpStatus.ACCEPTED);
    }

}
