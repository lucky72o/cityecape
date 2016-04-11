package com.cityescape.web.controller;

import com.cityescape.domain.Trip;
import com.cityescape.service.TripTagVotingService;
import com.cityescape.web.assemblers.TripResourceAssembler;
import com.cityescape.web.resource.TripResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Slava on 10/04/2016.
 */
@RestController
@Transactional
@RequestMapping(value = "/triptagvote", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripTagVoteController extends AbstractController {

    @Autowired
    private TripResourceAssembler tripResourceAssembler;

    @Autowired
    private TripTagVotingService tripTagVotingService;

    @RequestMapping(value = "/trip/{tripId}/triptagweight/{tripTagWeightId}", method = RequestMethod.POST)
    public HttpEntity<TripResource> voteForTripTagWeight(@PathVariable("tripId") Long tripId, @PathVariable("tripTagWeightId") Long tripTagWeightId) {

        Trip trip = tripTagVotingService.addVoteForTripTag(tripId, tripTagWeightId);

        TripResource resource = tripResourceAssembler.toResource(trip);
        tripResourceAssembler.addLinksToResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
