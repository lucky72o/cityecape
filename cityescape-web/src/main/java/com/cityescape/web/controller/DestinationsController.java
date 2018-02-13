package com.cityescape.web.controller;

import com.cityescape.domain.mongo.DestinationDocument;
import com.cityescape.enums.DistinationTagEnum;
import com.cityescape.enums.HolidayLengthEnum;
import com.cityescape.enums.TravelTypeEnum;
import com.cityescape.service.DestinationService;
import com.cityescape.web.assemblers.DestinationResourceAssembler;
import com.cityescape.web.resource.DestinationResource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestinationsController {

    public static final String HARDCODDED_CITY = "City";
    private final DestinationService destinationService;
    private final DestinationResourceAssembler destinationResourceAssembler;

    @Autowired
    public DestinationsController(DestinationService destinationService, DestinationResourceAssembler destinationResourceAssembler) {
        this.destinationService = destinationService;
        this.destinationResourceAssembler = destinationResourceAssembler;
    }

    @RequestMapping(method = GET)
    public HttpEntity<List<DestinationResource>> getDestinations(@RequestParam(required = false) String tags,
                                                                 @RequestParam(required = false) HolidayLengthEnum holidayLength,
                                                                 @RequestParam(required = false) TravelTypeEnum travelType,
                                                                 @RequestParam(required = false) Long travelTime) {


        Set<DistinationTagEnum> destinationTags = new HashSet<>();

        if (!StringUtils.isEmpty(tags)) {
            destinationTags.addAll(parseTags(tags));
        }

        List<DestinationDocument> destinations =
                destinationService.getDestinations(destinationTags, holidayLength, travelType, travelTime, HARDCODDED_CITY);

        return ResponseEntity.ok(destinationResourceAssembler.getDestinationResources(destinations));
    }

    private Set<DistinationTagEnum> parseTags(String tags) {
        return Stream.of(tags.split(","))
                .map(DistinationTagEnum::valueOf)
                .collect(toSet());
    }
}
