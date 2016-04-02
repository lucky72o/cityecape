package com.cityescape.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Slava on 14/02/2016.
 */

@Entity
@Table(name = "TRIP_TAG")
public class TripTag extends AbstractEntity<Long> implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TAG")
    @Size(min = 1, max = 255)
    private String tag;

    @Column(name = "DESCRIPTION")
    @Size(min = 0, max = 255)
    private String description;

    public TripTag(String tag) {
        this.tag = tag;
    }

    public TripTag(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    public TripTag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
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
                .append("tag", this.tag)
                .append("description", this.description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TripTag tripTag = (TripTag) o;

        return new EqualsBuilder()
                .append(this.id, tripTag.getId())
                .append(this.tag, tripTag.getTag())
                .append(this.description, tripTag.getDescription())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(tag)
                .append(description)
                .toHashCode();
    }
}
