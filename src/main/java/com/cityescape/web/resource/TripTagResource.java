package com.cityescape.web.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Slava on 18/02/2016.
 */
public class TripTagResource extends ResourceSupport {

    private Long tagId;
    private String tag;
    private String description;
    private String status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
