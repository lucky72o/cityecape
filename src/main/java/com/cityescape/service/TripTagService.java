package com.cityescape.service;

import com.cityescape.domain.TripTag;
import org.springframework.stereotype.Service;

/**
 * Created by Slava on 15/02/2016.
 */
public interface TripTagService {
    TripTag findByTag(String tag);

    TripTag create(TripTag tripTag);
}
