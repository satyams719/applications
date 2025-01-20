package com.basics.securitydemo.security;

import com.basics.securitydemo.enums.Permission;
import com.basics.securitydemo.model.Role;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.repository.UserRepository;
import com.basics.securitydemo.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public MongoUserDetailsService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRole());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {

        return role
                .getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }

}
