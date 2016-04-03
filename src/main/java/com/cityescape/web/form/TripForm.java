package com.cityescape.web.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripForm extends ResourceSupport {

    @Size(min = 1, max = 255)
    private String name;
    @NotEmpty(message = "tripTagWeights must not be empty")
    private Set<TripTagWeightForm> tripTagWeights = new HashSet<>();
    @Size(min = 1, max = 255)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TripTagWeightForm> getTripTagWeights() {
        return tripTagWeights;
    }

    public void setTripTagWeights(Set<TripTagWeightForm> tripTagWeights) {
        this.tripTagWeights = tripTagWeights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
