package com.cityescape.repository;

import com.cityescape.domain.TripTag;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Slava on 20/03/2016.
 */

public class TripTagRepositoryTest extends AbstractCityEscapeRepositoryTest {

    @Autowired
    private TripTagRepository tripTagRepository;

    private TripTag tripTag;

    @Before
    public void setUp() throws Exception {
        tripTag = new TripTag("Tag1", "description");

        tripTagRepository.save(tripTag);
    }

    @Test
    public void shouldFindTripTagByTagName() throws Exception {
        TripTag result = tripTagRepository.findByTag("Tag1");

        assertThat(result).isEqualTo(tripTag);
    }
}