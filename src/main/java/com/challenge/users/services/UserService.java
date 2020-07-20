package com.challenge.users.services;

import com.challenge.users.DTO.UserDTO;
import com.challenge.users.exception.UserExistsException;
import com.challenge.users.exception.UserNotExistsException;
import com.challenge.users.models.ERole;
import com.challenge.users.models.Role;
import com.challenge.users.models.User;
import com.challenge.users.payloads.requests.SignupRequest;
import com.challenge.users.repositories.RoleRepository;
import com.challenge.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    final RoleRepository roleRepository;
    private final EventService eventService;

    final PasswordEncoder encoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository, EventService eventService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.eventService = eventService;
    }

    @Transactional
    public User createUser(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            throw new UserExistsException("User already exists!");
        }

        User user = new User(signupRequest.getName(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found"));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role is not found"));
                    roles.add(adminRole);break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role is not found"));
                        roles.add(userRole);break;
                }
            });
        }

        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserDTO getUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotExistsException("User not found!"));
        return createUserDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotExistsException("User not found!"));
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        userRepository.save(user);
        return createUserDTO(user);
    }

    public void deleteAccount(String username) {
        boolean ifExists = userRepository.existsByEmail(username);
        if(ifExists) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UserNotExistsException("User not found"));
            eventService.deleteAllEventByUser(user);
            userRepository.delete(user);
        }
    }

    private UserDTO createUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
