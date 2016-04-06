package com.cityescape.web.support;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.service.TripTagService;
import com.cityescape.web.form.TripForm;
import com.cityescape.web.form.TripTagWeightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
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
        Set<TripTagWeight> tripTagWeights = trip.getTripTagWeights();
        tripTagWeights.clear();
        tripTagWeights.addAll(form.getTripTagWeights().stream()
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

    public TripForm toTripForm(Trip trip) {
        TripForm tripForm = new TripForm();
        tripForm.setName(trip.getName());
        tripForm.setDescription(trip.getDescription());
        addTripTagWeightsToTripForm(tripForm, trip);

        return tripForm;
    }

    private void addTripTagWeightsToTripForm(TripForm tripForm, Trip trip) {
        tripForm.getTripTagWeights().addAll((trip.getTripTagWeights().stream()
                .map(this::getTripTagWeightForm)
                .collect(Collectors.toSet())));
    }

    private TripTagWeightForm getTripTagWeightForm(TripTagWeight tripTagWeight) {
        TripTagWeightForm tripTagWeightForm = new TripTagWeightForm();
        tripTagWeightForm.setWeight(tripTagWeight.getWeight().doubleValue());
        tripTagWeightForm.setNumberOfVotes(tripTagWeight.getNumberOfVotes());
        tripTagWeightForm.setTripTagName(tripTagWeight.getTripTag().getTag());

        return tripTagWeightForm;
    }

    public void updateTrip(Trip trip, TripForm tripForm) {
        trip.setDescription(tripForm.getDescription());

        addTripTagWeightsToTrip(tripForm, trip);
    }
}
