package com.cityescape.web.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Slava on 28/03/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripTagWeightResource extends ResourceSupport {

    private Long tripTagWeightId;
    private TripTagResource tripTag;
    private double weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TripTagWeightResource that = (TripTagWeightResource) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(tripTagWeightId, that.tripTagWeightId)
                .append(tripTag, that.tripTag)
                .append(weight, that.weight)
                .append(numberOfVotes, that.numberOfVotes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(tripTagWeightId)
                .append(tripTag)
                .append(weight)
                .append(numberOfVotes)
                .toHashCode();
    }
}
