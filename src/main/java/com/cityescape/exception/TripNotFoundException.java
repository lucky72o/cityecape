package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Slava on 23/03/2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Trip not found")
public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(String message) {
        super(message);
    }
}
