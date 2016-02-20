package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Slava on 20/02/2016.
 */
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Illegal action")
public class EntityToSaveIsNullException extends RuntimeException {
    public EntityToSaveIsNullException(String message) {
        super(message);
    }
}
