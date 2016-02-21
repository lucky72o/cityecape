package com.cityescape.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;


/**
 * Date: 27/04/14
 * Time: 08:54
 */

public class AbstractResourceCollection<T> extends Resources<T> {

    private Integer totalItems;

    protected AbstractResourceCollection() {
    }

    protected AbstractResourceCollection(Iterable<T> content, Link... links) {
        super(content, links);
    }

    protected AbstractResourceCollection(Iterable<T> content, Iterable<Link> links) {
        super(content, links);
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

}
