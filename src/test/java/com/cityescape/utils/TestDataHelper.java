package com.cityescape.utils;

import com.cityescape.domain.TripTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 14/02/2016.
 */
public class TestDataHelper {

    public static TripTag getTripTag() {
        return new TripTag("testTag", "Test Tag");
    }

    public static TripTag getTripTagByName(String name) {
        return new TripTag(name, name + " Tag");
    }

    public static List<TripTag> getTripTags() {
        List<TripTag> tripTags = new ArrayList<>();
        tripTags.add(getTripTagByName("Tag1"));
        tripTags.add(getTripTagByName("Tag2"));
        return tripTags;
    }
}
