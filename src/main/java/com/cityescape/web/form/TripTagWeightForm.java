package com.cityescape.web.form;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripTagWeightForm extends ResourceSupport {

    @Size(min = 1, max = 255)
    private String tripTagName;

    @Range(min = 0, max = 1)
    @NotNull
    private double weight;

    @Range(min = 0, max = 999)
    private long numberOfVotes;

    public String getTripTagName() {
        return tripTagName;
    }

    public void setTripTagName(String tripTagName) {
        this.tripTagName = tripTagName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TripTagWeightForm that = (TripTagWeightForm) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(weight, that.weight)
                .append(numberOfVotes, that.numberOfVotes)
                .append(tripTagName, that.tripTagName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(tripTagName)
                .append(weight)
                .append(numberOfVotes)
                .toHashCode();
    }
}
