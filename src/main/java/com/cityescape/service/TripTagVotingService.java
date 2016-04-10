package com.cityescape.service;

import com.cityescape.domain.Trip;

/**
 * Created by Slava on 10/04/2016.
 */
public interface TripTagVotingService {
    Trip addVoteForTripTag(Long tripId, Long tripTagWeightId);
}
