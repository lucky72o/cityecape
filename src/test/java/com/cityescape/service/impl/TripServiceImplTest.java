package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.exception.IllegalTripActionException;
import com.cityescape.exception.TripNotFoundException;
import com.cityescape.enums.TripStatus;
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
    public static final Long TRIP_ID = 1L;

    @InjectMocks
    private TripService tripService = new TripServiceImpl();

    @Mock
    private TripRepository tripRepositoryMock;

    Trip trip;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        trip = TestDataHelper.getTrip(TRIP_NAME);
    }

    @Test(expected = TripNotFoundException.class)
    public void shouldThrowTripNotFoundExceptionIfNoTripByName() throws Exception {

        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(null);

        tripService.findByName(TRIP_NAME);
    }

    @Test
    public void shouldFindTripByName() throws Exception {

        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(trip);

        Trip result = tripService.findByName(TRIP_NAME);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(TRIP_NAME);
        assertThat(result.getDescription()).isEqualTo(trip.getDescription());
        assertThat(result.getId()).isEqualTo(trip.getId());
        assertThat(result.getTripTagWeights()).hasSize(2);

        verify(tripRepositoryMock).findByName(TRIP_NAME);
        verifyNoMoreInteractionsCommon();
    }

    @Test(expected = TripNotFoundException.class)
    public void shouldThrowTripNotFoundExceptionIfNoTripById() throws Exception {

        when(tripRepositoryMock.findOne(TRIP_ID)).thenReturn(null);

        tripService.findTripById(TRIP_ID);
    }

    @Test
    public void shouldFindTripById() throws Exception {

        when(tripRepositoryMock.findOne(TRIP_ID)).thenReturn(trip);

        Trip result = tripService.findTripById(TRIP_ID);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(trip.getName());
        assertThat(result.getDescription()).isEqualTo(trip.getDescription());
        assertThat(result.getId()).isEqualTo(trip.getId());
        assertThat(result.getTripTagWeights()).hasSize(2);

        verify(tripRepositoryMock).findOne(TRIP_ID);
        verifyNoMoreInteractionsCommon();
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
        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldCreateTrip() throws Exception {

        when(tripRepositoryMock.save(trip)).thenReturn(trip);

        Trip resultTrip = tripService.create(trip);

        assertThat(resultTrip).isNotNull();
        assertThat(resultTrip.getName()).isEqualTo(TRIP_NAME);
        assertThat(resultTrip.getDescription()).isEqualTo(trip.getDescription());
        assertThat(resultTrip.getTripTagWeights().size()).isEqualTo(trip.getTripTagWeights().size());

        verify(tripRepositoryMock).findByName(trip.getName());
        verify(tripRepositoryMock).save(trip);
        verifyNoMoreInteractionsCommon();
    }

    @Test(expected = DuplicateDataException.class)
    public void shouldThrowExceptionIfTripAlreadyExist() throws Exception {

        when(tripRepositoryMock.findByName(TRIP_NAME)).thenReturn(trip);

        tripService.create(trip);
    }

    @Test(expected = IllegalTripActionException.class)
    public void shouldThrowExceptionWhenDeleteAndTripIsNull() throws Exception {
        tripService.delete(null);
    }

    @Test(expected = IllegalTripActionException.class)
    public void shouldThrowExceptionWhenDeleteTripInActiveStatus() throws Exception {
        trip.setTripStatus(TripStatus.ACTIVE);

        tripService.delete(trip);
    }

    @Test
    public void shouldDeleteTrip() throws Exception {
        tripService.delete(trip);
        verify(tripRepositoryMock).delete(trip);
        verifyNoMoreInteractionsCommon();
    }

    @Test(expected = IllegalTripActionException.class)
    public void shouldThrowExceptionWhenUpdateAndTripIsNull() throws Exception {
        tripService.updateTrip(null);
    }

    @Test
    public void shouldUpdateTrip() throws Exception {
        when(tripRepositoryMock.save(trip)).thenReturn(trip);

        Trip resultTrip = tripService.updateTrip(trip);

        assertThat(resultTrip).isNotNull();
        assertThat(resultTrip.getName()).isEqualTo(TRIP_NAME);
        assertThat(resultTrip.getDescription()).isEqualTo(trip.getDescription());
        assertThat(resultTrip.getTripTagWeights().size()).isEqualTo(trip.getTripTagWeights().size());

        verify(tripRepositoryMock).save(trip);
        verifyNoMoreInteractionsCommon();

    }

    private void verifyNoMoreInteractionsCommon() {
        verifyNoMoreInteractions(tripRepositoryMock);
    }
}