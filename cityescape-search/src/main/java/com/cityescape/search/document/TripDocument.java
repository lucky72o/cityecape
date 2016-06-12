package com.cityescape.search.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Slava on 04/05/2016.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SolrDocument(solrCoreName = "cityescape")
public class TripDocument {

    @Id
    @Field
    private Long id;

    @Field
    private String name;

    @Field
    private List<String> tags = new LinkedList<>();

    @Dynamic
    @Field("*_d")
    private Map<String, BigDecimal> tagWeights = new HashMap<>();
}
