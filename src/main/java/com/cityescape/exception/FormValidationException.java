package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Date: 31/01/14
 * Time: 10:12
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class FormValidationException extends RuntimeException {
    public FormValidationException() {
    }

    public FormValidationException(String message) {
        super(message);
    }

    public FormValidationException(String message, Throwable chainedException) {
        super(message, chainedException);
    }
}
