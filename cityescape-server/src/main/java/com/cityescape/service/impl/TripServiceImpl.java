package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.IllegalTripActionException;
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
    public Trip findTripById(Long id) {
        Trip trip = tripRepository.findOne(id);

        if (trip == null) {
            throw new TripNotFoundException("Trip with id [ " + id + " ] not found");
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

        updateNumberOfVotes(trip);

        return tripRepository.save(trip);
    }

    @Override
    public void delete(Trip trip) {
        if (trip == null) {
            throw new IllegalTripActionException("Trip to delete must not be null.");
        }

        if (!trip.isValidToDelete()) {
            throw new IllegalTripActionException("Trip with name [ " + trip.getName() + " ] is not allowed to delete.");
        }

        tripRepository.delete(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalTripActionException("Trip to update must not be null.");
        }

        updateNumberOfVotes(trip);

        return tripRepository.save(trip);
    }

    private void updateNumberOfVotes(Trip trip) {
        trip.setNumberOfVotesForTags(trip.getTripTagWeights().stream()
                .mapToLong(TripTagWeight::getNumberOfVotes)
                .sum());
    }
}
