package com.cityescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Date: 31/01/14
 * Time: 10:12
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class IllegalVoteActionException extends RuntimeException {
    public IllegalVoteActionException() {
    }

    public IllegalVoteActionException(String message) {
        super(message);
    }

    public IllegalVoteActionException(String message, Throwable chainedException) {
        super(message, chainedException);
    }
}
