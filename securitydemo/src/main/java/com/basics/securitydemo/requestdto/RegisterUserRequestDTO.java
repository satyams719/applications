package com.basics.securitydemo.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegisterUserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;
    private String mobileNumber;
    private String address;
}
