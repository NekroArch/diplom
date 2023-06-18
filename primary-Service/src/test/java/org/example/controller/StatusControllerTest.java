package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.config.AppConfig;
import org.example.dao.StatusDao;
import org.example.entity.AddonsCategory;
import org.example.entity.Status;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("admin@gmail.com") //admin, create, update, delete
public class StatusControllerTest {
    @Autowired
    private StatusController statusController;
    @Autowired
    private StatusDao statusDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(statusController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Status status = Status.builder().status("testStatus").build();

        statusDao.save(status);

        mockMvc.perform(get("/status"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Status status = Status.builder().status("testStatus").build();

        var r = statusDao.save(status);

        mockMvc.perform(get("/status/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.status").value(r.getStatus()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {
        Status status = Status.builder().status("testStatus").build();

        var r = statusDao.save(status);

        mockMvc.perform(delete("/status/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = statusDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Status status = Status.builder().status("testStatus").build();

        var r = statusDao.save(status);

        String json = String.format(""" 
                {
                 "id":%s,
                 "status":"Status"
                }
                 """, r.getId());

        mockMvc.perform(put("/status/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.status").value("Status"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "status":"Status"
                }
                 """;

        mockMvc.perform(post("/status")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
