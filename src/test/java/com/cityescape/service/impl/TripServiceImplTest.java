package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.TripNotFoundException;
import com.cityescape.repository.TripRepository;
import com.cityescape.service.TripService;
import com.cityescape.utils.TestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Slava on 23/03/2016.
 */
public class TripServiceImplTest {

    public static final String TRIP_NAME = "London";

    @InjectMocks
    private TripService tripService = new TripServiceImpl();

    @Mock
    private TripRepository tripRepositoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = TripNotFoundException.class)
    public void shouldThrowTripNotFoundExceptionIfNoTripByName() throws Exception {

        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(null);

        tripService.findByName(TRIP_NAME);
    }

    @Test
    public void shouldFindTripByName() throws Exception {
        Trip trip = TestDataHelper.getTrip(TRIP_NAME);

        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(trip);

        Trip result = tripService.findByName(TRIP_NAME);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(TRIP_NAME);
        assertThat(result.getDescription()).isEqualTo(trip.getDescription());
        assertThat(result.getId()).isEqualTo(trip.getId());
        assertThat(result.getTripTagWeights()).hasSize(2);

        verify(tripRepositoryMock).findByName(TRIP_NAME);
        verifyNoMoreInteractions(tripRepositoryMock);
    }

    @Test
    public void shouldReturnAllTrips() throws Exception {
        List<Trip> mockTrips = TestDataHelper.getTrips();
        when(tripRepositoryMock.findAll()).thenReturn(mockTrips);

        List<Trip> resultTrips = tripService.findAllTrips();

        assertThat(resultTrips).isNotNull();
        assertThat(resultTrips).hasSize(2);
        assertThat(resultTrips.get(0).getName()).isEqualTo("Trip1");
        assertThat(resultTrips.get(1).getName()).isEqualTo("Trip2");

        verify(tripRepositoryMock).findAll();
        verifyNoMoreInteractions(tripRepositoryMock);
    }

    @Test
    public void shouldCreateTrip() throws Exception {

        Trip mockTrip = TestDataHelper.getTrip(TRIP_NAME);
        when(tripRepositoryMock.save(mockTrip)).thenReturn(mockTrip);

        Trip resultTrip = tripService.create(mockTrip);

        assertThat(resultTrip).isNotNull();
        assertThat(resultTrip.getName()).isEqualTo(TRIP_NAME);
        assertThat(resultTrip.getDescription()).isEqualTo(mockTrip.getDescription());
        assertThat(resultTrip.getTripTagWeights().size()).isEqualTo(mockTrip.getTripTagWeights().size());

        verify(tripRepositoryMock).findByName(mockTrip.getName());
        verify(tripRepositoryMock).save(mockTrip);
        verifyNoMoreInteractions(tripRepositoryMock);
    }

    @Test(expected = DuplicateDataException.class)
    public void shouldThrowExceptionIfTripAlreadyExist() throws Exception {

        Trip mockTrip = TestDataHelper.getTrip(TRIP_NAME);
        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(mockTrip);

        tripService.create(mockTrip);
    }
}