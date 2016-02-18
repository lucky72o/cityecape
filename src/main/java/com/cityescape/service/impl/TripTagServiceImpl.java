package com.cityescape.service.impl;

import com.cityescape.domain.TripTag;
import com.cityescape.repository.TripTagRepository;
import com.cityescape.service.TripTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Slava on 15/02/2016.
 */
@Service
public class TripTagServiceImpl implements TripTagService {

    @Autowired
    private TripTagRepository tripTagRepository;

    @Override
    public TripTag findByTag(String tag) {
        return tripTagRepository.findByTag(tag);
    }
}
