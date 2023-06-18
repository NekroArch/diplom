package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.config.AppConfig;
import org.example.dao.IngredientsDao;
import org.example.entity.AddonsCategory;
import org.example.entity.Ingredients;
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
public class IngredientsControllerTest {
    @Autowired
    private IngredientsController ingredientsController;
    @Autowired
    private IngredientsDao ingredientsDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientsController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Ingredients ingredients = Ingredients.builder()
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        ingredientsDao.save(ingredients);

        mockMvc.perform(get("/ingredients"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(4));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Ingredients ingredients = Ingredients.builder()
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        var r = ingredientsDao.save(ingredients);

        mockMvc.perform(get("/ingredients/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value(r.getName()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Ingredients ingredients = Ingredients.builder()
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        var r = ingredientsDao.save(ingredients);

        mockMvc.perform(delete("/ingredients/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = ingredientsDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Ingredients ingredients = Ingredients.builder()
                                             .name("text")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        var r = ingredientsDao.save(ingredients);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"newText",
                 "quantity":"5",
                 "measureUnits":{
                    "id":2
                 }
                }
                 """, r.getId());

        mockMvc.perform(put("/ingredients/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value("newText"))
               .andExpect(jsonPath("$.quantity").value("5"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "name":"test",
                 "quantity":"5",
                 "measureUnits":{
                    "id":2
                 }
                }
                 """;
        mockMvc.perform(post("/ingredients")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
