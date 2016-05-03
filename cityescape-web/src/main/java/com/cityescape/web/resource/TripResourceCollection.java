package com.cityescape.web.resource;

import org.springframework.hateoas.Link;

/**
 * Created by Slava on 28/03/2016.
 */
public class TripResourceCollection extends AbstractResourceCollection<TripResource> {
    public TripResourceCollection(Iterable<TripResource> content, Link... links) {
        super(content, links);
    }
}
