package com.challenge.users.controllers;

import com.challenge.users.DTO.UserDTO;
import com.challenge.users.payloads.responses.MessageResponse;
import com.challenge.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> get(Principal principal) {
        return new ResponseEntity<>(userService.getUser(principal.getName()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, Principal principal) {
        return ResponseEntity.ok(userService.updateUser(userDTO, principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Principal principal) {
        userService.deleteAccount(principal.getName());
        return ResponseEntity.ok(new MessageResponse("User successfully removed"));
    }
}
