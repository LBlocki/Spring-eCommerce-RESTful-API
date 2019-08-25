package com.blocki.springrestonlinestore.core.exceptions;

import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
