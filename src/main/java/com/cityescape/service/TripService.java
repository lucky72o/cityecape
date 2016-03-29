package com.cityescape.service;

import com.cityescape.domain.Trip;

import java.util.List;

/**
 * Created by Slava on 23/03/2016.
 */
public interface TripService {
    Trip findByName(String tripName);

    List<Trip> findAllTrips();

    Trip create(Trip mockTrip);
}
