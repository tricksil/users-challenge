package com.challenge.users.exception;

import com.challenge.users.errors.EventExistsExceptionDetails;
import com.challenge.users.errors.EventNotFoundExceptionDetails;
import com.challenge.users.errors.UserExistsExceptionDetails;
import com.challenge.users.errors.UserNotExistsExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class RestHandlerException {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity handleUserExistsException(UserExistsException userExistsException) {
        UserExistsExceptionDetails userExistsExceptionDetails = new UserExistsExceptionDetails();
        userExistsExceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        userExistsExceptionDetails.setTitle("Invalid user or password");
        userExistsExceptionDetails.setMessage(userExistsException.getMessage());
        userExistsExceptionDetails.setDetails(userExistsException.getClass().getName());

        return new ResponseEntity(userExistsExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity handleUserNotExistsException(UserNotExistsException userNotExistsException) {
        UserNotExistsExceptionDetails userNotExistsExceptionDetails = new UserNotExistsExceptionDetails();
        userNotExistsExceptionDetails.setStatus(HttpStatus.NOT_FOUND.value());
        userNotExistsExceptionDetails.setTitle("User");
        userNotExistsExceptionDetails.setMessage(userNotExistsException.getMessage());
        userNotExistsExceptionDetails.setDetails(userNotExistsException.getClass().getName());

        return new ResponseEntity(userNotExistsExceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFoundException(EventNotFoundException eventNotFoundException) {
        EventNotFoundExceptionDetails eventNotFoundExceptionDetails = new EventNotFoundExceptionDetails();
        eventNotFoundExceptionDetails.setStatus(HttpStatus.NOT_FOUND.value());
        eventNotFoundExceptionDetails.setTitle("Event");
        eventNotFoundExceptionDetails.setMessage(eventNotFoundException.getMessage());
        eventNotFoundExceptionDetails.setDetails(eventNotFoundException.getClass().getName());

        return new ResponseEntity(eventNotFoundExceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventExistsException.class)
    public ResponseEntity handleEventExistsException(EventExistsException eventExistsException) {
        EventExistsExceptionDetails eventExistsExceptionDetails = new EventExistsExceptionDetails();
        eventExistsExceptionDetails.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        eventExistsExceptionDetails.setTitle("Event");
        eventExistsExceptionDetails.setMessage(eventExistsException.getMessage());
        eventExistsExceptionDetails.setDetails(eventExistsException.getClass().getName());

        return new ResponseEntity(eventExistsExceptionDetails, HttpStatus.PRECONDITION_FAILED);
    }
}
