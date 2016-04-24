package com.cityescape.web.controller;

import com.cityescape.domain.PoeTag;
import com.cityescape.domain.Trip;
import com.cityescape.service.PoeTagService;
import com.cityescape.service.TripTagVotingService;
import com.cityescape.web.assemblers.TripResourceAssembler;
import com.cityescape.web.resource.TripResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.cityescape.web.support.NavigationLinkBuilder.linkToCurrentRequest;

/**
 * Created by Slava on 10/04/2016.
 */
@RestController
@Transactional
@RequestMapping(value = "/triptagvote", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripTagVoteController extends AbstractController {

    private TripResourceAssembler tripResourceAssembler;
    private TripTagVotingService tripTagVotingService;
    private PoeTagService poeTagService;

    @Autowired
    public TripTagVoteController(TripResourceAssembler tripResourceAssembler, TripTagVotingService tripTagVotingService, PoeTagService poeTagService) {
        this.tripResourceAssembler = tripResourceAssembler;
        this.tripTagVotingService = tripTagVotingService;
        this.poeTagService = poeTagService;
    }

    @RequestMapping(value = "/trip/{tripId}/triptagweight/{tripTagWeightId}/form", method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> getVoteForTripTagWeightForm(@PathVariable("tripId") Long tripId, @PathVariable("tripTagWeightId") Long tripTagWeightId) {

        String poeTag = poeTagService.createTag(ServletUriComponentsBuilder.fromCurrentRequest().build().toString());

        ResourceSupport resource = new ResourceSupport();

        resource.add(linkToCurrentRequest().withSelfRel());

        resource.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripTagVoteController.class)
                        .voteForTripTagWeight(poeTag, tripId, tripTagWeightId))
                .withRel("vote-for-trip-tag-action"));


        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/trip/{tripId}/triptagweight/{tripTagWeightId}/{poeTag}", method = RequestMethod.POST)
    public HttpEntity<TripResource> voteForTripTagWeight(@PathVariable("poeTag") String poeTag, @PathVariable("tripId") Long tripId, @PathVariable("tripTagWeightId") Long tripTagWeightId) {

        PoeTag poeTagRetrieved = poeTagService.getTag(poeTag);
        if (poeTagRetrieved == null || poeTagRetrieved.getConsumed()) {
            return methodNotAllowed();
        }

        poeTagService.consumeTag(poeTag);
        Trip trip = tripTagVotingService.addVoteForTripTag(tripId, tripTagWeightId);

        TripResource resource = tripResourceAssembler.toResource(trip);
        tripResourceAssembler.addLinksToResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
