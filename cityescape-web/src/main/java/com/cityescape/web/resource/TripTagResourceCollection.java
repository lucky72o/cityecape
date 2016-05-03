package com.cityescape.web.resource;

import org.springframework.hateoas.Link;


/**
 * Created by Slava on 18/02/2016.
 */
public class TripTagResourceCollection extends AbstractResourceCollection<TripTagResource> {

    public TripTagResourceCollection(Iterable<TripTagResource> content, Link... links) {
        super(content, links);
    }
}
