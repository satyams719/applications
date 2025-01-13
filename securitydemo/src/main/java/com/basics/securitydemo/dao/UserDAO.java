package com.basics.securitydemo.dao;

import com.basics.securitydemo.model.User;
import com.basics.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
       return userRepository.save(user);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public void updatePassword(String email, String newPassword) {
        Update update = new Update();
        update.set("password",newPassword);
        Query query = new Query(Criteria.where("email").is(email));
        mongoTemplate.upsert(query, update, User.class);
    }
}
