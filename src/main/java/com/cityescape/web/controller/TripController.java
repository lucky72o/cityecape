package com.cityescape.web.controller;

import com.cityescape.domain.PoeTag;
import com.cityescape.domain.Trip;
import com.cityescape.exception.FormValidationException;
import com.cityescape.service.PoeTagService;
import com.cityescape.service.TripService;
import com.cityescape.web.assemblers.TripResourceAssembler;
import com.cityescape.web.form.TripForm;
import com.cityescape.web.resource.TripResource;
import com.cityescape.web.resource.TripResourceCollection;
import com.cityescape.web.support.TripTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Slava on 28/03/2016.
 */

@RestController
@Transactional
@RequestMapping(value = "/trips", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController extends AbstractController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripResourceAssembler tripResourceAssembler;

    @Autowired
    private TripTransformer tripTransformer;

    @Autowired
    private PoeTagService poeTagService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<TripResourceCollection> getTrips() {
        List<Trip> trips = tripService.findAllTrips();
        TripResourceCollection tripResourceCollection = tripResourceAssembler.toResourceCollection(trips);

        return new ResponseEntity<>(tripResourceCollection, HttpStatus.OK);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public HttpEntity<TripForm> getCreateTripForm() {

        TripForm form = new TripForm();

        String poeTag = poeTagService.createTag(ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        tripResourceAssembler.addLinksToForm(form, poeTag);

        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @RequestMapping(value = "/{tripName}", method = RequestMethod.GET)
    public HttpEntity<TripResource> getTripByName(@PathVariable("tripName") String tripName) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/create/{poeTag}", method = RequestMethod.POST)
    public HttpEntity<TripResource> createTrip(@PathVariable("poeTag") String poeTag, @Valid @RequestBody TripForm form) {
        validateTripFor(form);
        PoeTag poeTagRetrieved = poeTagService.getTag(poeTag);
        if (poeTagRetrieved == null || poeTagRetrieved.getConsumed()) {
            return methodNotAllowed();
        }

        poeTagService.consumeTag(poeTag);
        Trip trip = tripService.create(tripTransformer.toEntity(form));
        TripResource tripResource = tripResourceAssembler.toResource(trip);

        return new ResponseEntity<>(tripResource, HttpStatus.CREATED);
    }

    // todo: move validateTripFor to separated validator
    private void validateTripFor(TripForm form) {
        form.getTripTagWeights().forEach(tripTagWeight -> {
            double weight = tripTagWeight.getWeight();
            if (weight < 0.1 || weight > 1) {
                throw new FormValidationException("Trip tag weight must be in range between 0.1 and 1.0");
            }
        });
    }
}
