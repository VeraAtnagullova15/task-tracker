package ru.atnagullova.tasktrackerbackend.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.atnagullova.tasktrackerbackend.exception.BadDataRequestException;
import ru.atnagullova.tasktrackerbackend.exception.InvalidJwtException;
import ru.atnagullova.tasktrackerbackend.exception.UsernameAlreadyExistsException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(AuthenticationException authenticationException) {

        log.error("Error while authenticated", authenticationException);
        ErrorMessage errorMessage = new ErrorMessage("Error while authenticated");

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(InvalidJwtException invalidJwtException) {

        log.error("Invalid JWT", invalidJwtException);
        ErrorMessage errorMessage = new ErrorMessage(invalidJwtException.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(UsernameNotFoundException usernameNotFoundException) {

        log.error("User wasn't found", usernameNotFoundException);
        ErrorMessage errorMessage = new ErrorMessage("User not found");

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(BadDataRequestException badDataRequestException) {

        log.error("Wrong data from user", badDataRequestException);
        ErrorMessage errorMessage = new ErrorMessage(badDataRequestException.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(UsernameAlreadyExistsException usernameAlreadyExistsException) {

        log.error("Username/email already taken", usernameAlreadyExistsException);
        ErrorMessage errorMessage = new ErrorMessage(usernameAlreadyExistsException.getMessage());

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(Exception e) {

        log.error("Unexpected error", e);
        ErrorMessage errorMessage = new ErrorMessage("Unexpected error");

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
