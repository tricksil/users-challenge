package com.challenge.users.controllers;

import com.challenge.users.DTO.UserDTO;
import com.challenge.users.config.JwtTokenUtil;
import com.challenge.users.models.User;
import com.challenge.users.payloads.requests.LoginRequest;
import com.challenge.users.payloads.requests.SignupRequest;
import com.challenge.users.payloads.responses.JwtResponse;
import com.challenge.users.services.UserDetailsImpl;
import com.challenge.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticationUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserDTO userDTO = new UserDTO(userDetails.getId(), userDetails.getName(), userDetails.getEmail());


        return ResponseEntity.ok(new JwtResponse(jwt, userDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        User user = userService.createUser(signupRequest);
        return new ResponseEntity<>(UserDTO.toUserDTO(user), HttpStatus.CREATED);
    }

}
