package com.basics.securitydemo.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String email;
}
