package com.cityescape.web.controller;


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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                andExpect(jsonPath("$.content[0].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[0].links[0].href", containsString("/trips/Trip1"))).
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
                andExpect(jsonPath("$.content[1].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[1].links[0].href", containsString("/trips/Trip2"))).
                andExpect(jsonPath("$.content[1].tripId", is(1))).
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

    // all mock services should be listed here
    private void verifyNoMoreInteractionsCommon() {
        verifyNoMoreInteractions(tripServiceMock);
        verifyNoMoreInteractions(tripTagServiceMock);
        verifyNoMoreInteractions(poeTagServiceMock);
    }
}