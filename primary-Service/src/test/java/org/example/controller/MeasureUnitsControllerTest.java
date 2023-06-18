package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.example.config.AppConfig;
import org.example.dao.MeasureUnitsDao;
import org.example.entity.AddonsCategory;
import org.example.entity.MeasureUnits;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("admin@gmail.com") //admin, create, update, delete
public class MeasureUnitsControllerTest {
    @Autowired
    private MeasureUnitsController measureUnitsController;
    @Autowired
    private MeasureUnitsDao measureUnitsDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(measureUnitsController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        measureUnitsDao.save(measureUnits);

        mockMvc.perform(get("/measureUnits"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(4));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        var r = measureUnitsDao.save(measureUnits);

        mockMvc.perform(get("/measureUnits/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.siUnit").value(r.getSiUnit()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        var r = measureUnitsDao.save(measureUnits);

        mockMvc.perform(delete("/measureUnits/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = measureUnitsDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        var r = measureUnitsDao.save(measureUnits);

        String json = String.format(""" 
                {
                 "id":%s,
                 "siUnit":"y"
                }
                 """, r.getId());

        mockMvc.perform(put("/measureUnits/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.siUnit").value("y"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "siUnit":"y"
                }
                 """;

        mockMvc.perform(post("/measureUnits")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
