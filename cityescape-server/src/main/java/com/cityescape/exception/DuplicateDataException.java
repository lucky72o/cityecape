package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Date: 31/01/14
 * Time: 10:12
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable chainedException) {
        super(message, chainedException);
    }
}
