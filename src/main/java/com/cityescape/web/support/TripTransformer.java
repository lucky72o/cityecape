package com.cityescape.web.support;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.service.TripTagService;
import com.cityescape.web.form.TripForm;
import com.cityescape.web.form.TripTagWeightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Created by Slava on 28/03/2016.
 */

@Component
public class TripTransformer extends AbstractTransformer<TripForm, Trip> {

    @Autowired
    private TripTagService tripTagService;

    @Override
    public Trip toEntity(TripForm form) {
        Trip trip = new Trip();

        trip.setName(form.getName());
        trip.setDescription(form.getDescription());
        addTripTagWeightsToTrip(form, trip);

        return trip;
    }

    private void addTripTagWeightsToTrip(TripForm form, Trip trip) {
        trip.setTripTagWeights(form.getTripTagWeights().stream()
                .map(tripTagWeightResource -> getTripTagWeight(trip, tripTagWeightResource))
                .collect(Collectors.toSet()));
    }

    private TripTagWeight getTripTagWeight(Trip trip, TripTagWeightForm tripTagWeightResource) {
        TripTagWeight tripTagWeight = new TripTagWeight();
        tripTagWeight.setWeight(BigDecimal.valueOf(tripTagWeightResource.getWeight()));
        tripTagWeight.setTrip(trip);
        tripTagWeight.setNumberOfVotes(tripTagWeightResource.getNumberOfVotes());
        tripTagWeight.setTripTag(tripTagService.findByTag(tripTagWeightResource.getTripTagName()));
        return tripTagWeight;
    }
}
