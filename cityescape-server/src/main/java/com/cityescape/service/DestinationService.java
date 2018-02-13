package com.cityescape.service;

import com.cityescape.domain.mongo.DestinationDocument;
import com.cityescape.enums.DistinationTagEnum;
import com.cityescape.enums.HolidayLengthEnum;
import com.cityescape.enums.TravelTypeEnum;

import java.util.List;
import java.util.Set;

public interface DestinationService {
    List<DestinationDocument> getDestinations(Set<DistinationTagEnum> tags, HolidayLengthEnum holidayLength, TravelTypeEnum travelType, Long travelTime, String city);
}
