package com.cityescape.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Slava on 20/03/2016.
 */
public class IndexControllerTest {


    @InjectMocks
    private IndexController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        controller = new IndexController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnApiIndex() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("triptags")))
                .andExpect(jsonPath("$.links[0].href", containsString("/triptags")))
                .andExpect(jsonPath("$.links[1].rel", is("trips")))
                .andExpect(jsonPath("$.links[1].href", containsString("/trips")));
    }
}