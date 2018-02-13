package com.cityescape.web.assemblers;

import com.cityescape.domain.mongo.DestinationDocument;
import com.cityescape.web.resource.DestinationResource;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class DestinationResourceAssembler {

    public List<DestinationResource> getDestinationResources(List<DestinationDocument> destinationDocuments) {
        return destinationDocuments.stream()
                .map(destinationDocument -> new DestinationResource(destinationDocument.getId(),
                        destinationDocument.getName(), destinationDocument.getDescription(),
                        destinationDocument.getImageUrl(), getTopCategories(destinationDocument.getTagScoreMap())))
                .collect(toList());
    }

    private List<String> getTopCategories(Map<String, Double> tagScoreMap) {
        return tagScoreMap.entrySet().stream()
                .sorted((e1,e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .limit(3)
                .collect(toList());
    }
}
