package com.cityescape.service;

import com.cityescape.domain.Trip;

import java.util.List;

/**
 * Created by Slava on 23/03/2016.
 */
public interface TripService {
    Trip findByName(String tripName);

    Trip findTripById(Long id);

    List<Trip> findAllTrips();

    Trip create(Trip mockTrip);

    void delete(Trip trip);

    Trip updateTrip(Trip trip);
}
