package org.example.controller;

import jakarta.servlet.ServletException;
import org.example.config.AppConfig;
import org.example.dao.PrivilegesDao;
import org.example.entity.Privileges;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("user@gmail.com") //user
public class PrivilegesControllerTest {
    @Autowired
    private PrivilegesController privilegesController;
    @Autowired
    private PrivilegesDao privilegesDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(privilegesController).build();
    }

    @Test(expected = ServletException.class)
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/privileges"));

    }

    @Transactional
    @Test(expected = ServletException.class)
    public void getByIdTest() throws Exception {
        Privileges privileges = Privileges.builder().name("newPrivilege").build();

        var r = privilegesDao.save(privileges);

        mockMvc.perform(get("/privileges/" + r.getId()))
               .andExpect(status().is4xxClientError())
               .andDo(print());

    }

    @Transactional
    @Test(expected = ServletException.class)
    public void deleteTest() throws Exception {

        Privileges privileges = Privileges.builder().name("newPrivilege").build();

        var r = privilegesDao.save(privileges);

        mockMvc.perform(delete("/privileges/" + r.getId()))
               .andExpect(status().is4xxClientError())
               .andDo(print());

    }

    @Transactional
    @Test(expected = ServletException.class)
    public void updateTest() throws Exception {

        Privileges privileges = Privileges.builder().name("newPrivilege").build();

        var r = privilegesDao.save(privileges);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"Privileges"
                }
                 """, r.getId());

        mockMvc.perform(put("/privileges/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is4xxClientError())
               .andDo(print());
    }

    @Transactional
    @Test(expected = ServletException.class)
    public void createTest() throws Exception {

        String json = """ 
                {
                 "name":"Privileges"
                }
                 """;

        mockMvc.perform(post("/privileges")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is4xxClientError())
               .andDo(print());
    }
}
