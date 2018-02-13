package com.cityescape.service.impl;

import com.cityescape.domain.mongo.DestinationDocument;
import com.cityescape.enums.DistinationTagEnum;
import com.cityescape.enums.HolidayLengthEnum;
import com.cityescape.enums.TravelTypeEnum;
import com.cityescape.repository.mongo.DestinationDocumentRepository;
import com.cityescape.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationDocumentRepository destinationDocumentRepository;

    @Autowired
    public DestinationServiceImpl(DestinationDocumentRepository destinationDocumentRepository) {
        this.destinationDocumentRepository = destinationDocumentRepository;
    }

    @Override
    public List<DestinationDocument> getDestinations(Set<DistinationTagEnum> tags,
                                                     HolidayLengthEnum holidayLength, TravelTypeEnum travelType,
                                                     Long travelTime, String startingPoint) {

        List<DestinationDocument> destinationDocuments = getDestinationDocuments(travelType, travelTime, startingPoint);

        return destinationDocuments.stream()
                .sorted(comparing(destinationDocument -> getDestinationScore((DestinationDocument) destinationDocument, tags, holidayLength)).reversed())
                .collect(toList());
    }

    private List<DestinationDocument> getDestinationDocuments(TravelTypeEnum travelType, Long travelTime, String startingPoint) {
        if (travelType != null) {
            switch (travelType) {
                case CAR:
                    return destinationDocumentRepository.findDestinationForCar(startingPoint, travelTime);
                case PUBLIC_TRANSPORT:
                    return destinationDocumentRepository.findDestinationForPublicTransport(startingPoint, travelTime);
            }
        }

        return destinationDocumentRepository.findAll();
    }

    private double getDestinationScore(DestinationDocument destinationDocument,
                                       Set<DistinationTagEnum> tags, HolidayLengthEnum holidayLength) {

        double tagScore = getDestinationScoreByTags(destinationDocument, tags);
        double holidayLengthScore = destinationDocument.getHolidayLengthScore(holidayLength);

        return holidayLengthScore + tagScore;
    }


    private double getDestinationScoreByTags(DestinationDocument destinationDocument, Set<DistinationTagEnum> tags) {
        Map<String, Double> tagScoreMap = destinationDocument.getTagScoreMap();

        return tags.stream()
                .map(tag -> tagScoreMap.get(tag.name()))
                .reduce((double) 0, (n1, n2) -> n1 + n2);
    }
}
