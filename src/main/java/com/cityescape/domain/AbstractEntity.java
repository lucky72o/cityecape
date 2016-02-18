package com.cityescape.domain;

import javax.persistence.Transient;
import java.io.Serializable;

public abstract class AbstractEntity<T extends Serializable> implements PersistentEntity<T> {

    @Transient
    public boolean isNew() {
        return getId() == null;
    }
}
