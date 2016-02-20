package com.cityescape.web.controller;

import com.cityescape.domain.TripTag;
import com.cityescape.web.resource.TripTagResource;
import com.cityescape.service.TripTagService;
import com.cityescape.web.assemblers.TripTagResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Slava on 15/02/2016.
 */
@RestController
@RequestMapping(value = "triptags")
public class TripTagController {

    @Autowired
    private TripTagService tripTagService;

    @Autowired
    TripTagResourceAssembler tripTagResourceAssembler;

    @RequestMapping(value = "/{tripTag}", method = RequestMethod.GET)
    public HttpEntity<TripTagResource> getTripTagByTag(@PathVariable("tripTag") String tripTagName) {

        TripTag tripTag = tripTagService.findByTag(tripTagName);
        TripTagResource resource = tripTagResourceAssembler.toResource(tripTag);

        tripTagResourceAssembler.addLinksToResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
