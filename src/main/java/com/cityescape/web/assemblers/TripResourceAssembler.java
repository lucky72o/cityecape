package com.cityescape.web.assemblers;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.web.controller.TripController;
import com.cityescape.web.form.TripForm;
import com.cityescape.web.resource.TripResource;
import com.cityescape.web.resource.TripResourceCollection;
import com.cityescape.web.resource.TripTagWeightResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.cityescape.web.support.NavigationLinkBuilder.linkToCurrentRequest;

/**
 * Created by Slava on 28/03/2016.
 */

@Component
public class TripResourceAssembler extends IdentifiableResourceAssemblerSupport<Trip, TripResource> {

    @Autowired
    private TripTagResourceAssembler tripTagResourceAssembler;

    public TripResourceAssembler() {
        super(TripController.class, TripResource.class);
    }

    @Override
    public TripResource toResource(Trip trip) {
        TripResource resource = new TripResource();

        resource.setName(trip.getName());
        resource.setDescription(trip.getDescription());
        resource.setTripId(trip.getId());
        resource.setStatus(trip.getTripStatus().name());
        addWeightsToResource(trip, resource);

        return resource;
    }

    public void addSelfLinkToFindByName(TripResource resource, Trip trip) {
        resource.add((ControllerLinkBuilder.linkTo((ControllerLinkBuilder.methodOn(TripController.class)
                .getTripByName(trip.getName()))).withSelfRel()));
    }

    public void addSelfLinkToFindById(TripResource resource, Trip trip) {
        resource.add((ControllerLinkBuilder.linkTo((ControllerLinkBuilder.methodOn(TripController.class)
                .getTripById(trip.getId()))).withSelfRel()));
    }

    public TripResourceCollection toResourceCollection(List<Trip> trips) {

        TripResourceCollection collection = new TripResourceCollection(
                trips.stream()
                        .map(trip -> {
                            TripResource resource = toResource(trip);
                            addSelfLinkToFindById(resource, trip);
                            addSelfLinkToFindByName(resource, trip);
                            return resource;
                        })
                        .collect(Collectors.toList())
        );

        addLinksToCollection(collection);
        return collection;
    }

    public void addLinksToForm(TripForm form, String poeTag) {
        form.add(linkToCurrentRequest().withSelfRel());

        form.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripController.class)
                        .createTrip(poeTag, form))
                .withRel("create-trip-action"));
    }

    public void addLinksToResource(TripResource resource) {
        resource.add(ControllerLinkBuilder
                .linkTo(TripController.class)
                .withRel("trips"));

        resource.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripController.class)
                        .deleteTripTagByName(resource.getName()))
                .withRel("delete-trip-action"));

        resource.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripController.class)
                        .updateTripForm(resource.getTripId()))
                .withRel("update-trip-form"));

    }

    private void addLinksToCollection(TripResourceCollection collection) {
        collection.setTotalItems(collection.getContent().size());

        collection.add(linkToCurrentRequest().withSelfRel());
        collection.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripController.class)
                        .getCreateTripForm())
                .withRel("trip-form"));
    }

    private void addWeightsToResource(Trip trip, TripResource resource) {
        resource.setTripTagWeights(trip.getTripTagWeights().stream()
                .map(this::getTripTagWeightResource)
                .collect(Collectors.toSet()));
    }

    private TripTagWeightResource getTripTagWeightResource(TripTagWeight tripTagWeight) {
        TripTagWeightResource tripTagWeightResource = new TripTagWeightResource();
        tripTagWeightResource.setTripTagWeightId(tripTagWeight.getId());
        tripTagWeightResource.setNumberOfVotes(tripTagWeight.getNumberOfVotes());
        tripTagWeightResource.setWeight(tripTagWeight.getWeight().doubleValue());
        tripTagWeightResource.setTripTag(tripTagResourceAssembler.toResource(tripTagWeight.getTripTag()));
        return tripTagWeightResource;
    }

    public void addLinksToUpdateForm(TripForm tripForm, String poeTag, long tripId) {
        tripForm.add(linkToCurrentRequest().withSelfRel());

        tripForm.add(ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(TripController.class)
                        .updateTrip(poeTag, tripId, tripForm))
                .withRel("update-trip-action"));
    }
}
