package org.example.controller;

import com.jayway.jsonpath.JsonPath;
import org.example.config.AppConfig;
import org.example.dao.RolesDao;
import org.example.entity.AddonsCategory;
import org.example.entity.Roles;
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
public class RolesControllerTest {
    @Autowired
    private RoleController roleController;
    @Autowired
    private RolesDao rolesDao;


    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Transactional
    @Test
    public void getAllTest() throws Exception {
        Roles roles = Roles.builder().name("newRole").build();

        rolesDao.save(roles);

        mockMvc.perform(get("/role"))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Transactional
    @Test
    public void getByIdTest() throws Exception {
        Roles roles = Roles.builder().name("newRole").build();

        var r = rolesDao.save(roles);

        mockMvc.perform(get("/role/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.name").value(r.getName()));

    }

    @Transactional
    @Test
    public void deleteTest() throws Exception {

        Roles roles = Roles.builder().name("newRole").build();

        var r = rolesDao.save(roles);

        mockMvc.perform(delete("/role/" + r.getId()))
               .andExpect(status().is2xxSuccessful())
               .andDo(print());

        var t = rolesDao.getById(r.getId());

        Assert.assertNull(t);
    }

    @Transactional
    @Test
    public void updateTest() throws Exception {

        Roles roles = Roles.builder().name("newRole").build();

        var r = rolesDao.save(roles);

        String json = String.format(""" 
                {
                 "id":%s,
                 "name":"Role"
                }
                 """, r.getId());

        mockMvc.perform(put("/role/" + r.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print()).andExpect(jsonPath("$.name").value("Role"));
    }

    @Transactional
    @Test
    public void createTest() throws Exception {

        String json = """ 
                {
                 "name":"Role"
                }
                 """;

        mockMvc.perform(post("/role")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(json)
               )
               .andExpect(status().is2xxSuccessful())
               .andDo(print())
               .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
