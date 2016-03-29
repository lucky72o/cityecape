package com.cityescape.web.form;

import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripTagWeightForm extends ResourceSupport {

    @Size(min = 1, max = 255)
    private String tripTagName;

    @Range(min = 0, max = 1)
    @NotNull
    private BigDecimal weight;

    @Range(min = 0, max = 999)
    private long numberOfVotes;

    public String getTripTagName() {
        return tripTagName;
    }

    public void setTripTagName(String tripTagName) {
        this.tripTagName = tripTagName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public long getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
