package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when E-tag not found
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "P-O-E-tag not found")
public class NoSuchTagException extends RuntimeException {
    public NoSuchTagException(String message) {
        super(message);
    }
}
