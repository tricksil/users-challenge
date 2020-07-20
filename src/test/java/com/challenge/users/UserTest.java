package com.challenge.users;

import com.challenge.users.DTO.UserDTO;
import com.challenge.users.payloads.requests.LoginRequest;
import com.challenge.users.payloads.requests.SignupRequest;
import com.challenge.users.payloads.responses.JwtResponse;
import com.challenge.users.services.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.challenge.users.util.Util.asJsonString;
import static com.challenge.users.util.Util.asObjectToken;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
    private static JwtResponse jwtResponse;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    public void case1ShouldRegisterUser() throws Exception {
        userService.deleteAccount("patrick@test.com");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signup")
                .header("Origin", "*")
                .content(asJsonString(new SignupRequest("Patrick Silva", "patrick@test.com", "123456")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Patrick Silva"));
    }

    @Test
    public void case2ShouldNotAllowAccessToUnauthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users")
                .header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void case3ShouldAuthenticateUser() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signin")
                .header("Origin", "*")
                .content(asJsonString(new LoginRequest("patrick@test.com", "123456")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userDTO.email").value("patrick@test.com"))
                .andReturn();
        jwtResponse = asObjectToken(result.getResponse().getContentAsString());
    }

    @Test
    public void case4ShouldGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users")
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Patrick Silva"));
    }

    @Test
    public void case5ShouldUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/users")
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .content(asJsonString(new UserDTO("Patrick Pereira", "patrick@test.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Patrick Pereira"));

    }

    @Test
    public void case6ShouldDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/users")
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User successfully removed"));
    }

    @Test
    public void case7ShouldRegisterUser() throws Exception {
        userService.deleteAccount("patrick@test.com");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/signup")
                .header("Origin", "*")
                .content(asJsonString(new SignupRequest("Patrick Silva", "patrick@test.com", "123456")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Patrick Silva"));
    }


}