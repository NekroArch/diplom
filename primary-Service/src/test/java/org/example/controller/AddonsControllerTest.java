package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.ServletException;
import org.example.config.AppConfig;
import org.example.dao.AddonsDao;
import org.example.entity.Addons;
import org.example.entity.AddonsCategory;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("user@gmail.com") //user
public class AddonsControllerTest {
    @Autowired
    private AddonsController addonsController;
    @Autowired
    private AddonsDao addonsDao;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(addonsController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();

        addonsDao.save(addons);

        mockMvc.perform(get("/addons"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();

        var r = addonsDao.save(addons);

        mockMvc.perform(get("/addons/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());
    }

    @Transactional
    @Test(expected = ServletException.class)
    public void deleteTest() throws Exception {

        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();

        var r = addonsDao.save(addons);

        mockMvc.perform(delete("/addons/" + r.getId()))
               .andExpect(status().is4xxClientError())
               .andDo(print());
    }

    @Transactional
    @Test(expected = ServletException.class)
    public void updateTest() throws Exception {

        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();


        var r = addonsDao.save(addons);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"newAddons",
                 "price":"5",
                 "category":{
                     "id":"1",
                     "name":null
                     }
                }
                 """, r.getId());

        mockMvc.perform(put("/addons/" + r.getId())
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
                 "name":"addons",
                 "price":"5",
                 "category":{
                     "id":"1",
                     "name":null
                     }
                }
                 """;

        mockMvc.perform(post("/addons")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(json)
                       ).andExpect(status().is4xxClientError())
                       .andDo(print());

    }
}
