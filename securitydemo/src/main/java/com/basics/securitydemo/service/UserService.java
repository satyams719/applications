package com.basics.securitydemo.service;

import com.basics.securitydemo.dao.UserDAO;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.requestdto.RegisterUserRequestDTO;
import com.basics.securitydemo.requestdto.UpdatePasswordRequestDTO;
import com.basics.securitydemo.responsedto.RegisterUserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";


    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) throws Exception {
        try{
        String password = generatePasswordForUser();
        User user = User.builder()
                .firstName(registerUserRequestDTO.getFirstName())
                .lastName(registerUserRequestDTO.getLastName())
                .email(registerUserRequestDTO.getEmail())
//                .role(registerUserRequestDTO.getRoles())
                .mobileNumber(registerUserRequestDTO.getMobileNumber())
                .address(registerUserRequestDTO.getAddress())
                .password(passwordEncoder.encode(password))
                .build();

        user = userDAO.registerUser(user);
        return createUserResponse(user, password);

        }catch (Exception e){
            throw new Exception("error while registering the user");
        }
    }

    private RegisterUserResponseDTO createUserResponse(User user, String password) {
        return new RegisterUserResponseDTO(user.getEmail(), password);
    }

    public String generatePasswordForUser(){
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    public String updateUserPassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        User user = userDAO.findByEmail(updatePasswordRequestDTO.getEmail());
        boolean isPasswordMatch = passwordEncoder.matches(updatePasswordRequestDTO.getOldPassword(), user.getPassword());
        if (isPasswordMatch){
            String newEncodedPassword = passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword());
            userDAO.updatePassword(updatePasswordRequestDTO.getEmail(), newEncodedPassword);
            return "password successfully updated";
        }
        return "old password was incorrect";

    }
}
