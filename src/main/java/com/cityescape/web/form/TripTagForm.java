package com.cityescape.web.form;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;

/**
 * Created by Slava on 20/02/2016.
 */
public class TripTagForm extends ResourceSupport {

    @Size(min = 1, max = 255)
    private String tag;
    @Size(min = 0, max = 255)
    private String description;

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
}
