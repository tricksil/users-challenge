package com.challenge.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String s) {
        super(s);
    }
}
