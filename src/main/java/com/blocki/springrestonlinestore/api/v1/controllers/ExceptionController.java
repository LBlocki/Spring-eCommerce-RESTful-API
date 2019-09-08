package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.core.exceptions.CustomErrorResponse;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.exceptions.ResourceAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(Exception exception) {

        if(log.isDebugEnabled()) {

            log.debug("Resource Not found exception caught : " + exception.getMessage() + "\n");
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceAlreadyExistsException(Exception exception) {

        if(log.isDebugEnabled()) {

            log.debug("Resource already exists exception caught : " + exception.getMessage() + "\n");
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(Exception exception) {

        if(log.isDebugEnabled()) {

            log.debug("Wrong number format exception caught : " + exception.getMessage() + "\n");
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {

        if(log.isDebugEnabled()) {

            log.debug("Not valid argument exception caught : " + exception.getMessage() + "\n");
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(log.isDebugEnabled()) {

            log.debug("Http message not readable exception caught : " + exception.getMessage() + "\n");
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, headers, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleConstraintViolationException(Exception exception){

        if(log.isDebugEnabled()) {

            log.debug("Constraint violation exception caught : " + exception.getMessage() + "\n");
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError("Username and email address must be unique. See stack trace: " + exception.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

