package com.javeriana.exceptions;

import java.lang.Exception;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message) {
        super(message);
    }

}
