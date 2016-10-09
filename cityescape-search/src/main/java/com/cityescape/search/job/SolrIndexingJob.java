package com.cityescape.search.job;

import com.cityescape.repository.TripRepository;
import com.cityescape.search.converter.TripDocumentConverter;
import com.cityescape.search.document.TripDocument;
import com.cityescape.search.repository.TripDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private TripDocumentConverter tripDocumentConverter;


    //todo: change to cron
//    @Scheduled(fixedRate = 600000)
    public void indexTrips() {

        // todo: return CompletableFuture
        Iterable<TripDocument> oldTripDocuments = tripDocumentRepository.findAll();

        List<TripDocument> tripDocuments = tripRepository.findAllActiveTrips()
                .map(tripDocumentConverter::convert)
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
}
