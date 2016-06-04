package com.cityescape.search.repository;

import com.cityescape.search.document.TripDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Slava on 04/05/2016.
 */

@Transactional
public interface TripDocumentRepository extends SolrCrudRepository<TripDocument, String> {

    TripDocument findByName(String name);
}
