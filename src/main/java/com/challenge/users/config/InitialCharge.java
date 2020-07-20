package com.challenge.users.config;

import com.challenge.users.models.ERole;
import com.challenge.users.models.Role;
import com.challenge.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitialCharge implements ApplicationListener<ContextRefreshedEvent> {

    final
    RoleRepository roleRepository;

    @Autowired
    public InitialCharge(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Role> roles = roleRepository.findAll();

//      Creating roles if they don't exist
        if (roles.isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }
}
