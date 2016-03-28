package com.cityescape.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Slava on 28/03/2016.
 */

@RestController
@RequestMapping(value = "/trips", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController extends AbstractController {
}
