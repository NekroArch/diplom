package org.example.controller;

import org.example.TestContainersConfig;
import org.example.config.AppConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.AddonsCategoryDao;
import org.example.entity.AddonsCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestContainersConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("admin@gmail.com") //admin, create, update, delete
@SpringBootTest
@Testcontainers
public class AddonsCategoryControllerTest {
    @Autowired
    private AddonsCategoryController addonsCategoryController;
    @Autowired
    private AddonsCategoryDao addonsCategoryDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    protected MockMvc mockMvc;
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(addonsCategoryController).build();
    }
    @Transactional
    @Test
    public void getAllTest() throws Exception {

        AddonsCategory addonsCategory = AddonsCategory.builder().name("name").build();

        addonsCategoryDao.save(addonsCategory);

        mockMvc.perform(get("/addonsCategory"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(3));


    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        AddonsCategory addonsCategory = AddonsCategory.builder().name("name").build();

        var r = addonsCategoryDao.save(addonsCategory);

        mockMvc.perform(get("/addonsCategory/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value(r.getName()));
    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        AddonsCategory addonsCategory = AddonsCategory.builder().name("name").build();

        var r = addonsCategoryDao.save(addonsCategory);

        mockMvc.perform(delete("/addonsCategory/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = addonsCategoryDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        AddonsCategory addonsCategory = AddonsCategory.builder().name("name").build();

        var r = addonsCategoryDao.save(addonsCategory);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"newName"
                }
                 """, r.getId());

        mockMvc.perform(put("/addonsCategory/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               ).andExpect(status().is2xxSuccessful())
               .andDo(print()).andExpect(jsonPath("$.name").value("newName"));

    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "name":"name"
                }
                 """;

        mockMvc.perform(post("/addonsCategory")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               ).andExpect(status().is2xxSuccessful())
               .andDo(print()).andExpect(jsonPath("$.id").isNotEmpty());
    }
}
