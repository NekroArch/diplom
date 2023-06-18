package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.RolesDao;
import org.example.entity.Roles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
public class RolesDaoTest {
    @Autowired
    private RolesDao rolesDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Roles roles = Roles.builder().name("newRole").build();
//
//        rolesDao.save(roles);
//
//        Assert.assertEquals(3, rolesDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Roles roles = Roles.builder().name("newRole").build();
//
//        rolesDao.save(roles);
//
//
//        Assert.assertEquals(3, rolesDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Roles roles = Roles.builder().name("newRole").build();

        Roles saved = rolesDao.save(roles);

        saved.setName("Role");

        rolesDao.update(saved);

        Roles newRole = rolesDao.getById(saved.getId());
        Assert.assertEquals("Role", newRole.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Roles roles = Roles.builder().name("newRole").build();
//
//        Roles saved = rolesDao.save(roles);
//
//        rolesDao.delete(saved.getId());
//
//        Assert.assertEquals(2, rolesDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Roles roles = Roles.builder().name("newRole").build();

        Roles saved = rolesDao.save(roles);

        Assert.assertEquals(saved, rolesDao.getById(saved.getId()));
    }

    @Transactional
    @Test
    public void findRoleWithFetchByIdTest() throws SQLException, InterruptedException {
        Roles roles = rolesDao.findRoleWithFetchById(1);
        Assert.assertEquals(roles, rolesDao.getById(1));
    }

    @Transactional
    @Test
    public void findByNameTest() throws SQLException, InterruptedException {
        Roles roles = rolesDao.findByName("admin");
        Assert.assertEquals(roles, rolesDao.getById(1));
    }
}