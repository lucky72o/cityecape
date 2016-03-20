package com.cityescape.web.controller;

import com.cityescape.domain.PoeTag;
import com.cityescape.domain.TripTag;
import com.cityescape.service.PoeTagService;
import com.cityescape.service.TripTagService;
import com.cityescape.utils.TestDataHelper;
import com.cityescape.web.assemblers.TripTagResourceAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Slava on 14/03/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class TripTagControllerTest {

    @Mock
    private TripTagService tripTagServiceMock;

    @Mock
    private PoeTagService poeTagServiceMock;

    @InjectMocks
    private TripTagController controller;

    private MockMvc mockMvc;

    private TripTag tripTag;

    public static final String POE_TAG = "6e3b8264d2nc8d2dee90f379c16e6b3e";

    @Before
    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ReflectionTestUtils.setField(controller, "tripTagResourceAssembler", new TripTagResourceAssembler());

        tripTag = TestDataHelper.getTripTagByName("Tag1");
        when(tripTagServiceMock.findAll()).thenReturn(TestDataHelper.getTripTags());
        when(tripTagServiceMock.findByTag("Tag1")).thenReturn(tripTag);
    }

    @Test
    public void shouldReturnAllTripTags() throws Exception {

        mockMvc.perform(get("/triptags")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/triptags"))).
                andExpect(jsonPath("$.links[1].rel", is("trip-tag-form"))).
                andExpect(jsonPath("$.links[1].href", containsString("/triptags/form"))).
                andExpect(jsonPath("$.content", hasSize(2))).
                andExpect(jsonPath("$.content[0].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[0].links[0].href", containsString("/triptags/Tag1"))).
                andExpect(jsonPath("$.content[0].tag", is("Tag1"))).
                andExpect(jsonPath("$.content[0].description", is("Tag1 Tag"))).
                andExpect(jsonPath("$.content[1].links[0].rel", is("self"))).
                andExpect(jsonPath("$.content[1].links[0].href", containsString("/triptags/Tag2"))).
                andExpect(jsonPath("$.content[1].tag", is("Tag2"))).
                andExpect(jsonPath("$.content[1].description", is("Tag2 Tag")));

        verify(tripTagServiceMock).findAll();
        verifyNoMoreInteractions(poeTagServiceMock, tripTagServiceMock);

    }

    @Test
    public void shouldReturnTripTagByTag() throws Exception {

        mockMvc.perform(get("/triptags/Tag1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(3))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/triptags/Tag1"))).
                andExpect(jsonPath("$.links[1].rel", is("triptags"))).
                andExpect(jsonPath("$.links[1].href", containsString("/triptags"))).
                andExpect(jsonPath("$.links[2].rel", is("delete-trip-tag-action"))).
                andExpect(jsonPath("$.links[2].href", containsString("/triptags/Tag1"))).
                andExpect(jsonPath("$.tag", is("Tag1"))).
                andExpect(jsonPath("$.description", is("Tag1 Tag")));

        verify(tripTagServiceMock).findByTag("Tag1");
        verifyNoMoreInteractions(poeTagServiceMock, tripTagServiceMock);

    }

    @Test
    public void shouldDeleteTripTagByTag() throws Exception {
        doNothing().when(tripTagServiceMock).delete(tripTag);

        mockMvc.perform(delete("/triptags/Tag1"))
                .andExpect(status().isNoContent());

        verify(tripTagServiceMock).findByTag("Tag1");
        verify(tripTagServiceMock).delete(tripTag);
        verifyNoMoreInteractions(poeTagServiceMock, tripTagServiceMock);

    }

    @Test
    public void shouldCreateTripTagForm() throws Exception {
        when(poeTagServiceMock.createTag(anyString())).thenReturn(POE_TAG);

        mockMvc.perform(get("/triptags/form")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.links", hasSize(2))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/triptags/form"))).
                andExpect(jsonPath("$.links[1].rel", is("create-trip-tag-action"))).
                andExpect(jsonPath("$.links[1].href", containsString("/triptags/create/" + POE_TAG)));

        verify(poeTagServiceMock).createTag(anyString());
        verifyNoMoreInteractions(poeTagServiceMock, tripTagServiceMock);

    }

    @Test
    public void shouldCreateTripTag() throws Exception {
        PoeTag poeTag = new PoeTag(POE_TAG);
        poeTag.setConsumed(false);

        when(poeTagServiceMock.getTag(POE_TAG)).thenReturn(poeTag);
        when(tripTagServiceMock.create(isA(TripTag.class))).thenReturn(tripTag);

        mockMvc.perform(post("/triptags/create/" + POE_TAG).
                contentType(MediaType.APPLICATION_JSON).
                content("{" +
                        "\"tag\": \"Tag1\"," +
                        "\"description\": \"Tag1 Tag\"" +
                        "}")).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.tag", is("Tag1"))).
                andExpect(jsonPath("$.description", is("Tag1 Tag"))).
                andExpect(jsonPath("$.links").exists()).
                andExpect(jsonPath("$.links", hasSize(1))).
                andExpect(jsonPath("$.links[0].rel", is("self"))).
                andExpect(jsonPath("$.links[0].href", containsString("/triptags/Tag1")));

        verify(poeTagServiceMock).getTag(POE_TAG);
        verify(poeTagServiceMock).consumeTag(POE_TAG);
        verify(tripTagServiceMock).create(any(TripTag.class));
        verifyNoMoreInteractions(poeTagServiceMock, tripTagServiceMock);
    }
}