package com.cityescape.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

/**
 * Abstract base class for REST controllers.
 */
public abstract class AbstractController {

    public ResponseEntity methodNotAllowed () {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Allow", Collections.singletonList("GET"));
        return new ResponseEntity<>(headers, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
