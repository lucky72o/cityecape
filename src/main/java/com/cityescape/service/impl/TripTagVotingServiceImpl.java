package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.exception.IllegalVoteActionException;
import com.cityescape.repository.TripTagWeightRepository;
import com.cityescape.service.TripService;
import com.cityescape.service.TripTagVotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Slava on 10/04/2016.
 */
@Component
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
                boolean tripTagWeightUpdated = false;

                trip = tripService.findTripById(tripId);
                Long newNumberOfVotes = trip.getNumberOfVotesForTags() + 1;
                trip.setNumberOfVotesForTags(newNumberOfVotes);

                for (TripTagWeight tripTagWeight : trip.getTripTagWeights()) {
                    if (tripTagWeight.getId().equals(tripTagWeightId)) {
                        long newNumberOfVotesForTag = tripTagWeight.getNumberOfVotes() + 1;
                        tripTagWeight.setNumberOfVotes(newNumberOfVotesForTag);
                        tripTagWeight.setWeight(BigDecimal.valueOf((double) newNumberOfVotesForTag / (double) newNumberOfVotes));

                        tripTagWeightUpdated = true;
                    } else {
                        tripTagWeight.setWeight(BigDecimal.valueOf((double) tripTagWeight.getNumberOfVotes() / (double) newNumberOfVotes));
                    }

                    tripTagWeightRepository.save(tripTagWeight);
                }

                if (!tripTagWeightUpdated) {
                    throw new IllegalVoteActionException("Can't process vote because trip tag weight for update doesn't exist. tripId: [ " + trip + " ] tripTagWeightId: [ " + tripTagWeightId + " ]");
                }

            }
        } finally {
            locks.remove(tripId);
        }

        return trip;
    }

    private Object getTripIdObjectForSync(final Long id) {
        locks.putIfAbsent(id, id);
        return locks.get(id);
    }
}
