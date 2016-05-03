package com.cityescape.repository;

import com.cityescape.domain.Trip;
import com.cityescape.utils.TestDataHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Slava on 23/03/2016.
 */
public class TripRepositoryTest extends AbstractCityEscapeRepositoryTest {

    public static final String TRIP_NAME = "London";

    @Autowired
    private TripRepository tripRepository;

    @Test
    public void shouldFindTripByName() throws Exception {
        Trip mockTrip = TestDataHelper.getTrip(TRIP_NAME);
        tripRepository.save(mockTrip);

        Trip result = tripRepository.findByName(TRIP_NAME);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(TRIP_NAME);
        assertThat(result.getId()).isEqualTo(mockTrip.getId());
        assertThat(result.getDescription()).isEqualTo(mockTrip.getDescription());
        assertThat(result.getTripTagWeights()).hasSize(2);
    }
}