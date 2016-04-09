package com.cityescape.domain;

import com.cityescape.enums.TripStatus;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 14/02/2016.
 */

@Entity
@Table(name = "TRIP")
public class Trip extends AbstractEntity<Long> implements Serializable {

    public Trip() {
        setTripStatus(TripStatus.NEW);
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRIP_STATUS")
    private TripStatus tripStatus;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("description", this.description)
                .append("tripStatus", this.tripStatus)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        return new EqualsBuilder()
                .append(id, trip.id)
                .append(name, trip.name)
                .append(tripTagWeights, trip.tripTagWeights)
                .append(description, trip.description)
                .append(tripStatus, trip.tripStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(tripTagWeights)
                .append(description)
                .append(tripStatus)
                .toHashCode();
    }

    public boolean isValidToDelete() {
        return !tripStatus.equals(TripStatus.ACTIVE);
    }

    public boolean isValidToUpdate() {
        return !tripStatus.equals(TripStatus.ACTIVE);
    }
}
