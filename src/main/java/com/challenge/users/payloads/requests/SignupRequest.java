package com.challenge.users.payloads.requests;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private Set<String> roles;

    @NotBlank
    private String password;

    public SignupRequest() {
    }

    public SignupRequest(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public SignupRequest(@NotBlank String name, @NotBlank String email, Set<String> roles, @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
