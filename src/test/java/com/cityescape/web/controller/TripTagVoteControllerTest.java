package com.cityescape.web.controller;

import com.cityescape.service.TripTagVotingService;
import com.cityescape.utils.TestDataHelper;
import com.cityescape.web.assemblers.TripResourceAssembler;
import com.cityescape.web.assemblers.TripTagResourceAssembler;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Slava on 11/04/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TripTagVoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TripTagVotingService tripTagVotingServiceMock;

    @InjectMocks
    private TripTagVoteController controller;

    @Before
    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        TripResourceAssembler tripResourceAssembler = new TripResourceAssembler();
        ReflectionTestUtils.setField(tripResourceAssembler, "tripTagResourceAssembler", new TripTagResourceAssembler());
        ReflectionTestUtils.setField(controller, "tripResourceAssembler", tripResourceAssembler);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldVoteForTripTagWeight() throws Exception {

        when(tripTagVotingServiceMock.addVoteForTripTag(1L, 11L)).thenReturn(TestDataHelper.getTrip("Oxford"));

        mockMvc.perform(post("/triptagvote/trip/1/triptagweight/11")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name", is("Oxford"))).
                andExpect(jsonPath("$.tripId", is(1))).
                andExpect(jsonPath("$.status", is("NEW"))).
                andExpect(jsonPath("$.description", is("description"))).
                andExpect(jsonPath("$.tripTagWeights", hasSize(2))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTagWeightId", is(22))).
                andExpect(jsonPath("$.tripTagWeights[0].weight", is(0.5))).
                andExpect(jsonPath("$.tripTagWeights[0].numberOfVotes", is(10))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[0].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTagWeightId", is(11))).
                andExpect(jsonPath("$.tripTagWeights[1].weight", is(0.5))).
                andExpect(jsonPath("$.tripTagWeights[1].numberOfVotes", is(10))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.tagId", is(100))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.description", is("Test Tag"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links", hasSize(1))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].rel", is("self"))).
                andExpect(jsonPath("$.tripTagWeights[1].tripTag.links[0].href", containsString("/triptags/testTag"))).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(3))).
                andExpect(jsonPath("$.links[0].rel", is("trips"))).
                andExpect(jsonPath("$.links[0].href", containsString("/trips"))).
                andExpect(jsonPath("$.links[1].rel", is("delete-trip-action"))).
                andExpect(jsonPath("$.links[1].href", containsString("/trips/Oxford"))).
                andExpect(jsonPath("$.links[2].rel", is("update-trip-form"))).
                andExpect(jsonPath("$.links[2].href", containsString("/trips/1/updateForm")));

        verify(tripTagVotingServiceMock).addVoteForTripTag(1L, 11L);
        verifyNoMoreInteractionsCommon();
    }

    // all mock services should be listed here
    private void verifyNoMoreInteractionsCommon() {
        verifyNoMoreInteractions(tripTagVotingServiceMock);
    }
}