package com.challenge.users;

import com.challenge.users.DTO.EventDTO;
import com.challenge.users.payloads.requests.LoginRequest;
import com.challenge.users.payloads.responses.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class EventTest {

    @Autowired
    private MockMvc mockMvc;

    private static JwtResponse jwtResponse;

    private static EventDTO eventDTO;

    @Test
    public void case1ShouldAuthenticateUser() throws Exception {
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
    public void case2ShouldGetEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/events")
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isEmpty());
    }

    @Test
    public void case3ShouldCreateEvent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/events")
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .content("{\"title\": \"Criando test\",\"date\": \"2020-07-30T13:30:00.000Z\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.title").value("Criando test"))
                .andReturn();
        eventDTO = new ObjectMapper().readValue(result.getResponse().getContentAsString(), EventDTO.class);
    }

    @Test
    public void case5ShouldGetEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/events/" + eventDTO.getId())
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Criando test"));
    }


    @Test
    public void case5ShouldUpdateEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/events/" + eventDTO.getId())
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .content("{\"title\": \"Criando test\",\"date\": \"2020-07-29T13:30:00.000Z\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value("2020-07-29T13:30:00"));
    }

    @Test
    public void case6ShouldRemoveEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/events/" + eventDTO.getId())
                .header("Origin", "*")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Event successfully removed"));
    }

}
