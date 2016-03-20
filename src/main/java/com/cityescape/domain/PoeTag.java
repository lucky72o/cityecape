package com.cityescape.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Slava on 20/02/2016.
 */

@Entity
@Table(name = "POE_TAG")
public class PoeTag extends AbstractEntity<Long> implements Serializable {

    public PoeTag() {
    }

    public PoeTag(String tag) {
        this.tag = tag;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TAG")
    @Size(min = 1, max = 255)
    private String tag;

    @Column(name = "CONSUMED")
    private Boolean consumed = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public Boolean getConsumed() {
        return consumed;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PoeTag poeTag = (PoeTag) o;

        return new EqualsBuilder()
                .append(tag, poeTag.tag)
                .append(consumed, poeTag.consumed)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tag)
                .append(consumed)
                .toHashCode();
    }
}
