package com.cityescape.web.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripResource extends ResourceSupport {

    private Long tripId;
    private String name;
    private Set<TripTagWeightResource> tripTagWeights = new HashSet<>();
    private String description;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TripTagWeightResource> getTripTagWeights() {
        return tripTagWeights;
    }

    public void setTripTagWeights(Set<TripTagWeightResource> tripTagWeights) {
        this.tripTagWeights = tripTagWeights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
