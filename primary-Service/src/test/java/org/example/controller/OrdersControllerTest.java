package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.ServletException;
import org.example.config.AppConfig;
import org.example.dao.OrdersDao;
import org.example.entity.AddonsCategory;
import org.example.entity.Orders;
import org.example.entity.Status;
import org.example.entity.Users;
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
@WithUserDetails("user@gmail.com") //user
public class OrdersControllerTest {
    @Autowired
    private OrdersController ordersController;
    @Autowired
    private OrdersDao ordersDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();
    }

    @Test(expected = ServletException.class)
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/orders"));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Orders orders = Orders.builder()
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();

        var r = ordersDao.save(orders);

        mockMvc.perform(get("/orders/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.price").value(r.getPrice()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Orders orders = Orders.builder()
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();

        var r = ordersDao.save(orders);

        mockMvc.perform(delete("/orders/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = ordersDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Orders orders = Orders.builder()
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();

        var r = ordersDao.save(orders);

        String json = String.format(""" 
                {
                 "id":%s,
                 "user":{
                    "id":"1"
                 },
                 "status":{
                    "id":"1"
                 },
                 "price":"10"
                }
                 """, r.getId());

        mockMvc.perform(put("/orders/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.price").value("10"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "users":{
                    "id":"1"
                 },
                 "status":{
                    "id":"1"
                 },
                 "price":"5"
                }
                 """;

        mockMvc.perform(post("/orders")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
