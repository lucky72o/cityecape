package com.cityescape.search.repository;

import com.cityescape.search.document.TripDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Slava on 04/05/2016.
 */
public class TripDocumentRepositoryTest extends AbstractCityEscapeSolrRepositoryTest {

//    @Autowired
//    private TripDocumentRepository tripDocumentRepository;
//
//    private TripDocument tripDocument;
//
//    @Before
//    public void setUp() throws Exception {
//        tripDocument = new TripDocument();
//        tripDocument.setId(1L);
//        tripDocument.setName("Tenerife");
//        tripDocument.setTags(Arrays.asList("shopping", "ocean"));
//
//        tripDocumentRepository.save(tripDocument);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        tripDocumentRepository.delete(tripDocument);
//
//    }
//
//    @Test
//    public void shouldFindTripByName() throws Exception {
//        TripDocument tripDocument = tripDocumentRepository.findByName("Tenerife");
//
//        assertThat(tripDocument).isNotNull();
//        assertThat(tripDocument.getName()).isEqualTo("Tenerife");
//        assertThat(tripDocument.getTags()).hasSize(2);
//        assertThat(tripDocument.getTags().get(0)).isEqualTo("shopping");
//        assertThat(tripDocument.getTags().get(1)).isEqualTo("ocean");
//    }
}