package com.cityescape.web.resource;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripTagWeightResource extends ResourceSupport {

    private Long tripTagWeightId;
    private TripTagResource tripTag;
    private BigDecimal weight;
    private Long numberOfVotes;

    public Long getTripTagWeightId() {
        return tripTagWeightId;
    }

    public void setTripTagWeightId(Long tripTagWeightId) {
        this.tripTagWeightId = tripTagWeightId;
    }

    public TripTagResource getTripTag() {
        return tripTag;
    }

    public void setTripTag(TripTagResource tripTag) {
        this.tripTag = tripTag;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
