package com.cityescape.search.job;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTag;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.repository.TripRepository;
import com.cityescape.search.document.TripDocument;
import com.cityescape.search.repository.TripDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;


/**
 * Created by Slava on 11/06/2016.
 */
@Component
@Transactional
public class SolrIndexingJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolrIndexingJob.class);

    @Autowired
    private TripDocumentRepository tripDocumentRepository;

    @Autowired
    private TripRepository tripRepository;


    //todo: change to cron
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() {

        // todo: return CompletableFuture
        Iterable<TripDocument> oldTripDocuments = tripDocumentRepository.findAll();

        List<TripDocument> tripDocuments = tripRepository.findAllActiveTrips()
                .map(this::convertToTripDocument)
                .collect(toList());

        LOGGER.info("Solr Indexing Job is in progress. " + tripDocuments.size() + " Trips will be indexed.");

        tripDocumentRepository.deleteAll();

        try {
            tripDocumentRepository.save(tripDocuments);
        } catch (Exception e) {
            LOGGER.error("Error while saving new Trip Documents. Trying to revert Trip Documents");
            tripDocumentRepository.deleteAll();
            tripDocumentRepository.save(oldTripDocuments);
        }

    }

    // todo move to converter class
    private TripDocument convertToTripDocument(Trip trip) {

        List<String> tripTags = trip.getTripTagWeights().stream()
                .map(TripTagWeight::getTripTag)
                .map(TripTag::getTag)
                .collect(toList());

        Map<String, BigDecimal> tagWeightMap = trip.getTripTagWeights().stream()
                .collect(toMap(tripTagWeight -> tripTagWeight.getTripTag().getTag(), TripTagWeight::getWeight));

        return new TripDocument(trip.getId(), trip.getName(), tripTags, tagWeightMap);
    }

}
