package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.TripNotFoundException;
import com.cityescape.repository.TripRepository;
import com.cityescape.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Slava on 23/03/2016.
 */

@Service
@Transactional
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip findByName(String tripName) {

        Trip trip = tripRepository.findByName(tripName);

        if (trip == null) {
            throw new TripNotFoundException("Trip with name [ " + tripName + " ] not found");
        }

        return trip;
    }

    @Override
    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip create(Trip trip) {
        Trip tripRetrieved = tripRepository.findByName(trip.getName());
        if (tripRetrieved != null) {
            throw new DuplicateDataException("Failed to create new trip. Trip with name [ " + trip.getName() + " ] is already exist");
        }
        return tripRepository.save(trip);
    }
}
