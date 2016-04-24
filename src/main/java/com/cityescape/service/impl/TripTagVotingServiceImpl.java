package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.exception.IllegalVoteActionException;
import com.cityescape.repository.TripTagWeightRepository;
import com.cityescape.service.TripService;
import com.cityescape.service.TripTagVotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Slava on 10/04/2016.
 */
@Service
@Transactional
public class TripTagVotingServiceImpl implements TripTagVotingService {

    private ConcurrentMap<Long, Long> locks = new ConcurrentHashMap<>();

    @Autowired
    private TripService tripService;

    @Autowired
    private TripTagWeightRepository tripTagWeightRepository;

    @Override
    public Trip addVoteForTripTag(Long tripId, Long tripTagWeightId) {

        Trip trip;

        try {
            synchronized (getTripIdObjectForSync(tripId)) {

                trip = tripService.findTripById(tripId);
                Long newTotalNumberOfVotes = trip.getNumberOfVotesForTags() + 1;
                trip.setNumberOfVotesForTags(newTotalNumberOfVotes);

                boolean tripTagWeightUpdated = updateTripTagWeights(tripTagWeightId, trip, newTotalNumberOfVotes);

                if (!tripTagWeightUpdated) {
                    throw new IllegalVoteActionException("Can't process vote because trip tag weight for update doesn't exist. tripId: [ " + trip + " ] tripTagWeightId: [ " + tripTagWeightId + " ]");
                }

            }
        } finally {
            locks.remove(tripId);
        }

        return trip;
    }

    private boolean updateTripTagWeights(Long tripTagWeightId, Trip trip, double newTotalNumberOfVotes) {
        boolean tripTagWeightUpdated = false;

        for (TripTagWeight tripTagWeight : trip.getTripTagWeights()) {
            TripTagWeight tripTagWeightRetrieved = tripTagWeightRepository.findOne(tripTagWeight.getId());
            if (tripTagWeightRetrieved.getId().equals(tripTagWeightId)) {
                long newNumberOfVotesForTag = tripTagWeight.getNumberOfVotes() + 1;
                tripTagWeightRetrieved.setNumberOfVotes(newNumberOfVotesForTag);
                tripTagWeightRetrieved.setWeight(BigDecimal.valueOf((double) newNumberOfVotesForTag / newTotalNumberOfVotes));

                tripTagWeightUpdated = true;
            } else {
                tripTagWeightRetrieved.setWeight(BigDecimal.valueOf((double) tripTagWeight.getNumberOfVotes() / newTotalNumberOfVotes));
            }
        }
        return tripTagWeightUpdated;
    }

    private Object getTripIdObjectForSync(final Long id) {
        locks.putIfAbsent(id, id);
        return locks.get(id);
    }
}
