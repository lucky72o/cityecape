package com.cityescape.service;

import com.cityescape.domain.TripTag;

import java.util.List;

/**
 * Created by Slava on 15/02/2016.
 */
public interface TripTagService {
    TripTag findByTag(String tag);

    TripTag create(TripTag tripTag);

    List<TripTag> findAll();

    void delete(TripTag tripTag);
}
