package com.cityescape.service.impl;

import com.cityescape.domain.Trip;
import com.cityescape.domain.TripTagWeight;
import com.cityescape.exception.IllegalVoteActionException;
import com.cityescape.exception.TripNotFoundException;
import com.cityescape.repository.TripTagWeightRepository;
import com.cityescape.service.TripService;
import com.cityescape.service.TripTagVotingService;
import com.cityescape.utils.TestDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Slava on 10/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TripTagVotingServiceImplTest {

    @Mock
    private TripService tripServiceMock;

    @Mock
    private TripTagWeightRepository tripTagWeightRepositoryMock;

    @InjectMocks
    private TripTagVotingService tripTagVotingService = new TripTagVotingServiceImpl();

    @Test
    public void shouldUpdateVoteForTripTag() throws Exception {
        Trip oxford = TestDataHelper.getTrip("Oxford");
        when(tripServiceMock.findTripById(1L)).thenReturn(oxford);

        Trip updatedTrip = tripTagVotingService.addVoteForTripTag(1L, 11L);

        assertThat(updatedTrip).isNotNull();
        assertThat(updatedTrip.getTripTagWeights()).hasSize(2);
        assertThat(updatedTrip.getNumberOfVotesForTags()).isEqualTo(21);

        //assert that vote updated correctly

        long sumNumberOfVotes = updatedTrip.getTripTagWeights().stream()
                .mapToLong(TripTagWeight::getNumberOfVotes).sum();

        assertThat(sumNumberOfVotes).isEqualTo(21);

        double weightSum = updatedTrip.getTripTagWeights().stream()
                .mapToDouble(tripTagWeight -> tripTagWeight.getWeight().doubleValue()).sum();

        assertThat(weightSum).isEqualTo(1.0);
        Iterator<TripTagWeight> iterator = updatedTrip.getTripTagWeights().iterator();
        assertThat(iterator.next().getWeight()).isNotEqualTo(iterator.next().getWeight());

        iterator = updatedTrip.getTripTagWeights().iterator();
        assertThat(iterator.next().getNumberOfVotes()).isNotEqualTo(iterator.next().getNumberOfVotes());

        verify(tripServiceMock).findTripById(1L);
        verify(tripTagWeightRepositoryMock, times(2)).save(any(TripTagWeight.class));
        verifyNoMoreInteractionsCommon();
    }

    @Test(expected = IllegalVoteActionException.class)
    public void shouldThrowExceptionWhenVoteForTripTagIfWeightNotFound() throws Exception {
        Trip oxford = TestDataHelper.getTrip("Oxford");
        when(tripServiceMock.findTripById(1L)).thenReturn(oxford);

        tripTagVotingService.addVoteForTripTag(1L, 1L);
    }

    private void verifyNoMoreInteractionsCommon() {
        verifyNoMoreInteractions(tripServiceMock, tripTagWeightRepositoryMock);
    }
}