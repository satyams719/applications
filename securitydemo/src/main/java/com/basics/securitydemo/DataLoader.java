package com.basics.securitydemo;

import com.basics.securitydemo.model.User;
import com.basics.securitydemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
//
//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//            userRepository.save(new User("user", passwordEncoder.encode("password"), new String[]{"USER"}));
//            userRepository.save(new User("admin", passwordEncoder.encode("password"), new String[]{"ADMIN"}));
//        };
//    }
}

