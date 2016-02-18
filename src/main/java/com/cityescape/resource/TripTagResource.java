package com.cityescape.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Slava on 18/02/2016.
 */
public class TripTagResource extends ResourceSupport {

    private Long tagId;
    private String tag;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
