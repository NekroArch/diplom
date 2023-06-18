package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.ServletException;
import org.example.config.AppConfig;
import org.example.dao.CartsDao;
import org.example.entity.AddonsCategory;
import org.example.entity.Carts;
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
public class CartsControllerTest {
    @Autowired
    private CartsController cartsController;

    @Autowired
    private CartsDao cartsDao;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartsController).build();
    }

    @Test(expected = ServletException.class)
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/carts"));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Carts cart = Carts.builder()
                          .user(Users.builder().id(2).build())
                          .build();

        var r = cartsDao.save(cart);

        mockMvc.perform(get("/carts/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = cartsDao.getById(r.getId());

        Assert.assertNotNull(t);

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Carts cart = Carts.builder()
                          .user(Users.builder().id(2).build())
                          .build();

        var r = cartsDao.save(cart);

        mockMvc.perform(delete("/carts/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = cartsDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Carts cart = Carts.builder()
                          .user(Users.builder().id(2).build())
                          .build();

        var r = cartsDao.save(cart);

        String json = String.format(""" 
                {
                 "id":%s,
                 "user":{
                    "id":3
                 }
                }
                 """, r.getId());

        mockMvc.perform(put("/carts/" + r.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().is2xxSuccessful()).andDo(print());

        var t = cartsDao.getById(r.getId());

        Assert.assertEquals(cart.getId(), t.getId());
        Assert.assertEquals("3", t.getUser().getId().toString());
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "user":{
                    "id":2
                 }
                }
                 """;

        var r = mockMvc.perform(post("/carts")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(json)
                       ).andExpect(status().is2xxSuccessful())
                       .andDo(print()).andReturn();

        String content = r.getResponse().getContentAsString();
        Integer id = JsonPath.read(content, "$.id");

        var t = cartsDao.getById(id);

        Assert.assertNotNull(t);
    }
}
