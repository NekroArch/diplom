package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.config.AppConfig;
import org.example.dao.UserDao;
import org.example.entity.AddonsCategory;
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
@WithUserDetails("admin@gmail.com") //admin, create, update, delete
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private UserDao userDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        userDao.save(user);

        mockMvc.perform(get("/users"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        var r = userDao.save(user);

        mockMvc.perform(get("/users/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.firstName").value(r.getFirstName()));
    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        var r = userDao.save(user);

        mockMvc.perform(delete("/users/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = userDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        var r = userDao.save(user);

        String json = String.format(""" 
                {
                 "id":%s,
                 "firstName":"newFirstName",
                 "lastName":"newLastName",
                 "mail":"31323@gmail.com",
                 "phone":"322",
                 "password":"24342523"
                }
                 """, r.getId());

        mockMvc.perform(put("/users/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.firstName").value("newFirstName"))
               .andExpect(jsonPath("$.lastName").value("newLastName"))
               .andExpect(jsonPath("$.phone").value("322"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "firstName":"newFirstName",
                 "lastName":"newLastName",
                 "mail":"31323@gmail.com",
                 "phone":"322",
                 "password":"24342523"
                }
                 """;

        mockMvc.perform(post("/users")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
