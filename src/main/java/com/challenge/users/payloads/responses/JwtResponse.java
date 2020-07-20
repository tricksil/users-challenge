package com.challenge.users.payloads.responses;

import com.challenge.users.DTO.UserDTO;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private UserDTO userDTO;

    public JwtResponse() {
    }

    public JwtResponse(String token,UserDTO userDTO) {
        this.token = token;
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
