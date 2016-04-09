package com.cityescape.web.controller;


import com.cityescape.domain.PoeTag;
import com.cityescape.domain.Trip;
import com.cityescape.exception.DuplicateDataException;
import com.cityescape.service.PoeTagService;
import com.cityescape.service.TripService;
import com.cityescape.service.TripTagService;
import com.cityescape.utils.TestDataHelper;
import com.cityescape.web.assemblers.TripResourceAssembler;
import com.cityescape.web.assemblers.TripTagResourceAssembler;
import com.cityescape.web.support.TripTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Slava on 02/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TripControllerTest {

    @Mock
    private TripService tripServiceMock;

    @Mock
    private TripTagService tripTagServiceMock;

    @Mock
    private PoeTagService poeTagServiceMock;

    @InjectMocks
    TripController controller;

    private MockMvc mockMvc;

    public static final String POE_TAG = "6e3b8264d2nc8d2dee90f379c16e6b3e";

    @Before
    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        TripResourceAssembler tripResourceAssembler = new TripResourceAssembler();
        ReflectionTestUtils.setField(tripResourceAssembler, "tripTagResourceAssembler", new TripTagResourceAssembler());
        TripTransformer tripTransformer = new TripTransformer();
        ReflectionTestUtils.setField(tripTransformer, "tripTagService", tripTagServiceMock);

        ReflectionTestUtils.setField(controller, "tripTransformer", tripTransformer);
        ReflectionTestUtils.setField(controller, "tripResourceAssembler", tripResourceAssembler);

    }

    @Test
    public void shouldReturnAllTrips() throws Exception {

        when(tripServiceMock.findAllTrips()).thenReturn(TestDataHelper.getTrips());

        mockMvc.perform(get("/trips")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips"))).
                andExpect(jsonPath("$.links[1].rel", is("trip-form"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/form"))).
                andExpect(jsonPath("$.content", hasSize(2))).
                andExpect(jsonPath("$.content[0].links", hasSize(2))).
                andExpect(jsonPath("$.content[0].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[0].links[0].href", containsString("/trips/1"))).
                andExpect(jsonPath("$.content[0].links[1].rel", is("find-by-name"))).
                andExpect(jsonPath("$.content[0].links[1].href", containsString("/trips/byName/Trip1"))).
                andExpect(jsonPath("$.content[0].tripId", is(1))).
                andExpect(jsonPath("$.content[0].name", is("Trip1"))).
                andExpect(jsonPath("$.content[0].tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[0].tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].weight", is(0.2))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].numberOfVotes", is(100))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[0].tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.content[1].links", hasSize(2))).
                andExpect(jsonPath("$.content[1].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[1].links[0].href", containsString("/trips/2"))).
                andExpect(jsonPath("$.content[1].links[1].rel", is("find-by-name"))).
                andExpect(jsonPath("$.content[1].links[1].href", containsString("/trips/byName/Trip2"))).
                andExpect(jsonPath("$.content[1].tripId", is(2))).
                andExpect(jsonPath("$.content[1].name", is("Trip2"))).
                andExpect(jsonPath("$.content[1].tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[1].tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].weight", is(0.2))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].numberOfVotes", is(100))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[1].tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag")));

        verify(tripServiceMock).findAllTrips();
        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldReturnTripForm() throws Exception {

        when(poeTagServiceMock.createTag(anyString())).thenReturn(POE_TAG);

        mockMvc.perform(get("/trips/form")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/form"))).
                andExpect(jsonPath("$.links[1].rel", is("create-trip-action"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/create/" + POE_TAG)));

        verify(poeTagServiceMock).createTag(anyString());
        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldThrowExceptionWhenCreateTripWithSameName() throws Exception {
        PoeTag poeTag = new PoeTag(POE_TAG);
        poeTag.setConsumed(false);

        when(poeTagServiceMock.getTag(POE_TAG)).thenReturn(poeTag);
        when(tripServiceMock.create(any(Trip.class))).thenThrow(new DuplicateDataException("Failed to create new trip. Trip with name Oxford is already exist"));

        mockMvc.perform(post("/trips/create/" + POE_TAG).
                contentType(MediaType.APPLICATION_JSON).
                content("{" +
                        "\"name\": \"Oxford\"," +
                        "\"tripTagWeights\": " +
                        "[" +
                        "{" +
                        "\"tripTagName\": \"food\"," +
                        "\"weight\": \"0.6\"," +
                        "\"numberOfVotes\": \"100\"" +
                        "}" +
                        "]" +
                        "," +
                        "\"description\": \"Great historical town\"" +
                        "}")).
                andExpect(status().isConflict());

        verify(poeTagServiceMock).getTag(POE_TAG);
        verify(poeTagServiceMock).consumeTag(POE_TAG);
        verify(tripServiceMock).create(any(Trip.class));
        verify(tripTagServiceMock).findByTag("food");
        verifyNoMoreInteractionsCommon();

    }

    @Test
    public void shouldThrowExceptionWhenCreateTripWithTagWeighMoreThenOne() throws Exception {

        mockMvc.perform(post("/trips/create/" + POE_TAG).
                contentType(MediaType.APPLICATION_JSON).
                content("{" +
                        "\"name\": \"Oxford\"," +
                        "\"tripTagWeights\": " +
                        "[" +
                        "{" +
                        "\"tripTagName\": \"food\"," +
                        "\"weight\": \"2\"," +
                        "\"numberOfVotes\": \"100\"" +
                        "}" +
                        "]" +
                        "," +
                        "\"description\": \"Great historical town\"" +
                        "}")).
                andExpect(status().isConflict());

        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldCreateTrip() throws Exception {
        PoeTag poeTag = new PoeTag(POE_TAG);
        poeTag.setConsumed(false);

        when(poeTagServiceMock.getTag(POE_TAG)).thenReturn(poeTag);
        when(tripServiceMock.create(any(Trip.class))).thenReturn(TestDataHelper.getTrip("Oxford"));

        mockMvc.perform(post("/trips/create/" + POE_TAG).
                contentType(MediaType.APPLICATION_JSON).
                content("{" +
                        "\"name\": \"Oxford\"," +
                        "\"tripTagWeights\": " +
                        "[" +
                        "{" +
                        "\"tripTagName\": \"food\"," +
                        "\"weight\": \"0.6\"," +
                        "\"numberOfVotes\": \"100\"" +
                        "}" +
                        "]" +
                        "," +
                        "\"description\": \"Great historical town\"" +
                        "}")).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("find-by-name"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/byName/Oxford"))).
                andExpect(jsonPath("$.links[1].rel", is("self"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/1"))).
                andExpect(jsonPath("$.tripId", is(1))).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.tripTagWeights[1].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[1].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag")));

        verify(poeTagServiceMock).getTag(POE_TAG);
        verify(poeTagServiceMock).consumeTag(POE_TAG);
        verify(tripServiceMock).create(any(Trip.class));
        verify(tripTagServiceMock).findByTag("food");
        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldDeleteTrip() throws Exception {
        when(tripServiceMock.findByName("London")).thenReturn(TestDataHelper.getTrip("London"));

        mockMvc.perform(delete("/trips/London").
                header("API_VERSION", "v1")).
                andExpect(status().isNoContent());

        verify(tripServiceMock).findByName("London");
        verify(tripServiceMock).delete(any(Trip.class));
        verifyNoMoreInteractionsCommon();

    }

    @Test
    public void shouldReturnUpdateTripForm() throws Exception {

        when(poeTagServiceMock.createTag(anyString())).thenReturn(POE_TAG);
        when(tripServiceMock.findTripById(1L)).thenReturn(TestDataHelper.getTrip("Oxford"));

        mockMvc.perform(get("/trips/1/updateForm")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.description", is("description"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTagName", is("testTag"))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/1/updateForm"))).
                andExpect(jsonPath("$.links[1].rel", is("update-trip-action"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/1/update/" + POE_TAG)));

        verify(poeTagServiceMock).createTag(anyString());
        verify(tripServiceMock).findTripById(1L);
        verifyNoMoreInteractionsCommon();
    }

    @Test
    public void shouldReturnTripByName() throws Exception {

        when(tripServiceMock.findByName("Oxford")).thenReturn(TestDataHelper.getTrip("Oxford"));

        mockMvc.perform(get("/trips/byName/Oxford")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.tripId", is(1))).
                andExpect(jsonPath("$.status", is("NEW"))).
                andExpect(jsonPath("$.description", is("description"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.tripTagWeights[1].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[1].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.links", hasSize(4))).
                andExpect(jsonPath("$.links[0].rel", is("find-by-name"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/byName/Oxford"))).
                andExpect(jsonPath("$.links[1].rel", is("trips"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips"))).
                andExpect(jsonPath("$.links[2].rel", is("delete-trip-action"))).
                andExpect(jsonPath("$.links[2].href", containsString("/trips/Oxford"))).
                andExpect(jsonPath("$.links[3].rel", is("update-trip-form"))).
                andExpect(jsonPath("$.links[3].href", containsString("/trips/1/updateForm")));

        verify(tripServiceMock).findByName("Oxford");
        verifyNoMoreInteractionsCommon();

    }

    @Test
    public void shouldReturnTripById() throws Exception {

        when(tripServiceMock.findTripById(1L)).thenReturn(TestDataHelper.getTrip("Oxford"));

        mockMvc.perform(get("/trips/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.tripId", is(1))).
                andExpect(jsonPath("$.status", is("NEW"))).
                andExpect(jsonPath("$.description", is("description"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.tripTagWeights[1].weight", is(0.2))).
                andExpect(jsonPath("$.tripTagWeights[1].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.links", hasSize(4))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/1"))).
                andExpect(jsonPath("$.links[1].rel", is("trips"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips"))).
                andExpect(jsonPath("$.links[2].rel", is("delete-trip-action"))).
                andExpect(jsonPath("$.links[2].href", containsString("/trips/Oxford"))).
                andExpect(jsonPath("$.links[3].rel", is("update-trip-form"))).
                andExpect(jsonPath("$.links[3].href", containsString("/trips/1/updateForm")));

        verify(tripServiceMock).findTripById(1L);
        verifyNoMoreInteractionsCommon();

    }

    @Test
    public void shouldUpdateTrip() throws Exception {

        PoeTag poeTag = new PoeTag(POE_TAG);
        poeTag.setConsumed(false);

        when(poeTagServiceMock.getTag(POE_TAG)).thenReturn(poeTag);
        Trip trip = TestDataHelper.getTrip("Oxford");
        when(tripServiceMock.findTripById(1L)).thenReturn(trip);
        when(tripServiceMock.updateTrip(trip)).thenReturn(trip);
        when(tripTagServiceMock.findByTag("food")).thenReturn(TestDataHelper.getTripTagByName("food"));

        mockMvc.perform(put("/trips/1/update/" + POE_TAG).
                contentType(MediaType.APPLICATION_JSON).
                content("{" +
                        "\"name\": \"Oxford\"," +
                        "\"tripTagWeights\": " +
                        "[" +
                        "{" +
                        "\"tripTagName\": \"food\"," +
                        "\"weight\": \"0.6\"," +
                        "\"numberOfVotes\": \"100\"" +
                        "}" +
                        "]" +
                        "," +
                        "\"description\": \"Great historical town\"" +
                        "}")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("find-by-name"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips/byName/Oxford"))).
                andExpect(jsonPath("$.links[1].rel", is("self"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/1"))).
                andExpect(jsonPath("$.tripId", is(1))).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.description", is("Great historical town"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.6))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.description", is("food Tag"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/food")));

        verify(poeTagServiceMock).getTag(POE_TAG);
        verify(poeTagServiceMock).consumeTag(POE_TAG);
        verify(tripServiceMock).findTripById(1L);
        verify(tripServiceMock).updateTrip(any(Trip.class));
        verify(tripTagServiceMock).findByTag("food");

        verifyNoMoreInteractionsCommon();
    }

    // all mock services should be listed here
    private void verifyNoMoreInteractionsCommon() {
        verifyNoMoreInteractions(tripServiceMock);
        verifyNoMoreInteractions(tripTagServiceMock);
        verifyNoMoreInteractions(poeTagServiceMock);
    }
}