package com.cityescape.domain;

import org.springframework.hateoas.Identifiable;

import java.io.Serializable;


public interface PersistentEntity<T extends Serializable> extends Identifiable<T> {
    boolean isNew();
}
