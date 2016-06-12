package com.cityescape.repository;

import com.cityescape.domain.Trip;
import com.cityescape.enums.TripStatus;
import com.cityescape.utils.TestDataHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by Slava on 23/03/2016.
 */
@Rollback
public class TripRepositoryTest extends AbstractCityEscapeRepositoryTest {

    public static final String TRIP_NAME = "TestTrip";

    @Autowired
    private TripRepository tripRepository;

    @Test
    public void shouldFindTripByName() throws Exception {
        Trip mockTrip = TestDataHelper.getSimpleTrip(TRIP_NAME);
        tripRepository.saveAndFlush(mockTrip);

        Trip result = tripRepository.findByName(TRIP_NAME);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(TRIP_NAME);
        assertThat(result.getDescription()).isEqualTo(mockTrip.getDescription());
    }

    @Test
    public void shouldFindAllActiveTrips() throws Exception {

        // prepare data to return
        Trip newTrip = TestDataHelper.getSimpleTrip(TRIP_NAME);

        Trip activeTrip = TestDataHelper.getSimpleTrip("Minsk");
        activeTrip.setTripStatus(TripStatus.ACTIVE);

        tripRepository.saveAndFlush(activeTrip);
        tripRepository.saveAndFlush(newTrip);

        // execute the method
        List<Trip> allActiveTrips = tripRepository.findAllActiveTrips()
                .collect(Collectors.toList());

        // asserts
        assertThat(allActiveTrips).isNotNull();
        assertThat(allActiveTrips).hasSize(1);
    }
}