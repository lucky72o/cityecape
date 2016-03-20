package com.cityescape.web.controller;

import com.cityescape.web.resource.VoidResource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Slava on 18/02/2016.
 */

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<VoidResource> getApiIndex() {

        VoidResource index = new VoidResource();

        index.add(ControllerLinkBuilder.linkTo(TripTagController.class)
                .withRel("triptags"));

        return new ResponseEntity<>(index, HttpStatus.OK);
    }

}
