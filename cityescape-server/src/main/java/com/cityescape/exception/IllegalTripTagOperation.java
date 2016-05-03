package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Slava on 09/04/2016.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class IllegalTripTagOperation extends RuntimeException {
    public IllegalTripTagOperation() {

    }

    public IllegalTripTagOperation(String message) {
        super(message);
    }

    public IllegalTripTagOperation(String message, Throwable chainedException) {
        super(message, chainedException);
    }
}
