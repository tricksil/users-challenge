package com.challenge.users.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EventDTO  implements Serializable {

    private String id;
    private String title;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private UserDTO userDTO;

    public EventDTO() {
    }

    public EventDTO(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    public EventDTO(String id, String title, LocalDateTime date, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.userDTO = userDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
