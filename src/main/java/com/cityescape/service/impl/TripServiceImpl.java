package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.exception.TripNotFoundException;
import com.cityescape.repository.TripRepository;
import com.cityescape.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 23/03/2016.
 */

@Service
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
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }
}
