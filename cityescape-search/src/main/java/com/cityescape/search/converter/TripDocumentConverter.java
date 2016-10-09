package com.cityescape.search.converter;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTag;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.search.document.TripDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Slava on 16/06/2016.
 */

@Component
public class TripDocumentConverter implements Converter<Trip, TripDocument> {


    @Override
    public TripDocument convert(Trip trip) {
        List<String> tripTags = trip.getTripTagWeights().stream()
                .map(TripTagWeight::getTripTag)
                .map(TripTag::getTag)
                .collect(toList());

        Map<String, BigDecimal> tagWeightMap = trip.getTripTagWeights().stream()
                .collect(toMap(tripTagWeight -> tripTagWeight.getTripTag().getTag(), TripTagWeight::getWeight));

        return new TripDocument(trip.getId(), trip.getName(), tripTags, tagWeightMap);
    }
}
