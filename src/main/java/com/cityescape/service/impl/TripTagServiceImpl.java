package com.cityescape.service.impl;

import com.cityescape.domain.TripTag;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.EntityToSaveIsNullException;
import com.cityescape.repository.TripTagRepository;
import com.cityescape.service.TripTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 15/02/2016.
 */
@Service
@Transactional
public class TripTagServiceImpl implements TripTagService {

    @Autowired
    private TripTagRepository tripTagRepository;

    @Override
    public TripTag findByTag(String tag) {
        return tripTagRepository.findByTag(tag);
    }

    @Override
    public TripTag create(TripTag tripTag) {
        if (tripTag == null) {
            throw new EntityToSaveIsNullException("TripTag must not be null.");
        }

        TripTag existingTripTag = tripTagRepository.findByTag(tripTag.getTag());

        if (existingTripTag != null) {
            throw new DuplicateDataException("TripTag is already present based on the tag [ " + tripTag.getTag() + " ]");
        }

        return tripTagRepository.save(tripTag);
    }
}
