package com.cityescape.repository.mongo;

import com.cityescape.domain.mongo.DestinationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DestinationDocumentRepository extends MongoRepository<DestinationDocument, String> {

    @Query("{travelTimes.startingPoint: ?0,travelTimes.carTravelTime:{ $lte: ?1}}")
    List<DestinationDocument> findDestinationForCar(String startingPoint, long travelTime);

    @Query("{travelTimes.startingPoint: ?0,travelTimes.publicTransportTravelTime:{ $lte: ?1}}")
    List<DestinationDocument> findDestinationForPublicTransport(String startingPoint, long travelTime);
}
