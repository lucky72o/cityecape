package com.cityescape.search.job;

import com.cityescape.domain.Trip;
import com.cityescape.repository.TripRepository;
import com.cityescape.search.converter.TripDocumentConverter;
import com.cityescape.search.document.TripDocument;
import com.cityescape.search.repository.TripDocumentRepository;
import com.cityescape.utils.TestDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Slava on 16/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SolrIndexingJobTest {

    @Mock
    private TripDocumentRepository tripDocumentRepositoryMock;

    @Mock
    private TripRepository tripRepositoryMock;

    @Mock
    private TripDocumentConverter tripDocumentConverterMock;

    @InjectMocks
    private SolrIndexingJob solrIndexingJob = new SolrIndexingJob();

    @Captor
    private ArgumentCaptor<List<TripDocument>> tripDocumentsArgumentCaptor;

    @Test
    public void shouldImportActiveTrips() throws Exception {

        // Prepare data
        List<Trip> trips = TestDataHelper.getActiveTrips();
        TripDocument tripDocument = new TripDocument();

        // When
        when(tripRepositoryMock.findAllActiveTrips()).thenReturn(trips.stream());
        when(tripDocumentConverterMock.convert(any(Trip.class))).thenReturn(tripDocument);

        // Execute the method
        solrIndexingJob.indexTrips();

        // Assert / Verify
        verify(tripDocumentRepositoryMock).deleteAll();
        verify(tripDocumentRepositoryMock).save(tripDocumentsArgumentCaptor.capture());

        assertThat(tripDocumentsArgumentCaptor.getValue()).isNotNull();
        assertThat(tripDocumentsArgumentCaptor.getValue()).hasSize(2);
    }
}