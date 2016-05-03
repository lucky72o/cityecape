package com.cityescape.service.impl;

import com.cityescape.domain.TripTag;
import com.cityescape.exception.EntityToSaveIsNullException;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.IllegalTripTagOperation;
import com.cityescape.repository.TripTagRepository;
import com.cityescape.service.TripTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Slava on 15/02/2016.
 */
@Service
@Transactional
public class TripTagServiceImpl implements TripTagService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TripTagServiceImpl.class);

    @Autowired
    private TripTagRepository tripTagRepository;

    @Override
    public TripTag findByTag(String tag) {
        LOGGER.info("Retrieving a trip tag by tag name {}", tag);
        TripTag tripTag = tripTagRepository.findByTag(tag);

        if (tripTag == null) {
            throw new DataRetrievalFailureException("Trip Tag tag name [" + tag + "] is not valid.");
        }

        return tripTag;
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

    @Override
    public List<TripTag> findAll() {
        return tripTagRepository.findAll();
    }

    @Override
    public void delete(TripTag tripTag) {
        if (tripTag == null) {
            throw new IllegalTripTagOperation("Trip tag to delete must not be null.");
        }

        if (!tripTag.isValidToDelete()) {
            throw new IllegalTripTagOperation("Trip tag with tag [ " + tripTag.getTag() + " ] is not allowed to delete.");
        }
        tripTagRepository.delete(tripTag);
    }
}
