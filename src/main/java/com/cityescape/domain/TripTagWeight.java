package com.cityescape.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Slava on 14/03/2016.
 */

@Entity
@Table(name = "TRIP_TAG_WEIGHT")
public class TripTagWeight implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "TRIP_TAG_ID")
    private TripTag tripTag;

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "TRIP_ID")
    private Trip trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TripTag getTripTag() {
        return tripTag;
    }

    public void setTripTag(TripTag tripTag) {
        this.tripTag = tripTag;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tripTag", tripTag)
                .append("weight", weight)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TripTagWeight that = (TripTagWeight) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(tripTag, that.tripTag)
                .append(weight, that.weight)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tripTag)
                .append(weight)
                .toHashCode();
    }
}
