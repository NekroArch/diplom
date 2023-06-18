package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.config.AppConfig;
import org.example.dao.DishesDao;
import org.example.entity.Dishes;
import org.example.entity.Menu;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@WithUserDetails("admin@gmail.com") //admin, create, update, delete
public class DishesControllerTest {
    @Autowired
    private DishesController dishesController;
    @Autowired
    private DishesDao dishesDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishesController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        dishesDao.save(dishes);

        mockMvc.perform(get("/dish"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        var r = dishesDao.save(dishes);

        mockMvc.perform(get("/dish/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value(r.getName()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        var r = dishesDao.save(dishes);

        mockMvc.perform(delete("/dish/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = dishesDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        var r = dishesDao.save(dishes);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"newTest",
                 "price":"5",
                 "menu":{
                    "id":1
                 }
                }
                 """, r.getId());

        mockMvc.perform(put("/dish/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value("newTest"));

    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "name":"test",
                 "price":"5",
                 "menu":{
                    "id":1
                 },
                }
                 """;

        mockMvc.perform(post("/dish")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               ).andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());

    }
}
