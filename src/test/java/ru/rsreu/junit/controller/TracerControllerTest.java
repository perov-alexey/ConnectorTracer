package ru.rsreu.junit.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.algorithms.BranchAndBoundAlgorithm;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/config.xml")
public class TracerControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testTracerControllerAccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Field field = FixtureProvider.getTopChannelOverloadedField();
        String fieldJson = mapper.writeValueAsString(field);
        MvcResult result = this.mockMvc.perform(post("/traced")
                .content(fieldJson)
                .contentType(MediaType.APPLICATION_JSON)
                .param("algorithmType",  "BRANCH_AND_BOUND"))
            .andExpect(status().isOk())
            .andReturn();
        List<Field> tracedFields = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Field>>(){});
        List<Field> manuallyTracedFields = new BranchAndBoundAlgorithm().execute(field, false, true);
        assertEquals("Controller return wrong traced field", manuallyTracedFields, tracedFields);
    }

}
