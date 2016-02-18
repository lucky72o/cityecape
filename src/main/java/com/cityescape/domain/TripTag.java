package com.cityescape.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
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
//    @SequenceGenerator(name = "betMoneySeq", sequenceName = "BET_MONEY_ID_SEQ")
    private Long id;

    @Column(name = "TAG")
    private String tag;

    public TripTag(String tag) {
        this.tag = tag;
    }

    public TripTag() {
    }

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "TripTag{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TripTag tripTag = (TripTag) o;

        return new EqualsBuilder()
                .append(id, tripTag.id)
                .append(tag, tripTag.tag)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tag)
                .toHashCode();
    }
}
