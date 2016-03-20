package com.cityescape.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 14/02/2016.
 */

@Entity
@Table(name = "TRIP")
public class Trip {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripTagWeight> tripTagWeights = new HashSet<>();

    @Column(name = "DESCRIPTION")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TripTagWeight> getTripTagWeights() {
        return tripTagWeights;
    }

    public void setTripTagWeights(Set<TripTagWeight> tripTagWeights) {
        this.tripTagWeights = tripTagWeights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("description", this.description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return new EqualsBuilder()
                .append(id, trip.id)
                .append(tripTagWeights, trip.tripTagWeights)
                .append(description, trip.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tripTagWeights)
                .append(description)
                .toHashCode();
    }
}
