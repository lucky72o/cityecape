package com.cityescape.web.resource;

import java.util.LinkedList;
import java.util.List;

public class DestinationResource {

    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private List<String> topCategories = new LinkedList<>();

    public DestinationResource() {
    }

    public DestinationResource(String id, String name, String description, String imageUrl, List<String> topCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.topCategories = topCategories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTopCategories() {
        return topCategories;
    }

    public void setTopCategories(List<String> topCategories) {
        this.topCategories = topCategories;
    }
}
