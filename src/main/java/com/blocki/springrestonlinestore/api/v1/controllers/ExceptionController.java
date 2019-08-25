package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(Exception exception) {

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException(Exception exception) {

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }


}
