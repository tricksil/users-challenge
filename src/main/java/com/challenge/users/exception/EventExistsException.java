package com.challenge.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class EventExistsException extends RuntimeException{

    public EventExistsException(String s) {
        super(s);
    }
}
