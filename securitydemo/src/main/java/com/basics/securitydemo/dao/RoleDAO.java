package com.basics.securitydemo.dao;

import com.basics.securitydemo.model.Role;
import com.basics.securitydemo.repository.RoleRepository;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Component
public class RoleDAO {

    private final RoleRepository repository;

    public RoleDAO(RoleRepository repository) {
        this.repository = repository;
    }

    public Role saveRole(Role role){
        return repository.save(role);
    }

    public Role getRolesById(Long parentRoleId) throws RoleNotFoundException {

        Optional<Role> optionalRole = repository.findById(parentRoleId);
        if (optionalRole.isPresent()){
            return optionalRole.get();
        }else{
            throw new RoleNotFoundException("user role doesn't exist");
        }
    }
}
