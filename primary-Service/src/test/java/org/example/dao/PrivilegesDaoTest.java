package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.PrivilegesDao;
import org.example.entity.Privileges;
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
public class PrivilegesDaoTest {
    @Autowired
    private PrivilegesDao privilegesDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Privileges privileges = Privileges.builder().name("newPrivilege").build();
//
//        privilegesDao.save(privileges);
//
//        Assert.assertEquals(5, privilegesDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Privileges privileges = Privileges.builder().name("newPrivilege").build();
//
//        privilegesDao.save(privileges);
//
//
//        Assert.assertEquals(5, privilegesDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Privileges privileges = Privileges.builder().name("newPrivilege").build();

        Privileges saved = privilegesDao.save(privileges);

        saved.setName("Privilege");

        privilegesDao.update(saved);

        Privileges newPrivileges = privilegesDao.getById(saved.getId());
        Assert.assertEquals("Privilege", newPrivileges.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Privileges privileges = Privileges.builder().name("newPrivilege").build();
//
//        Privileges saved = privilegesDao.save(privileges);
//
//        privilegesDao.delete(saved.getId());
//
//        Assert.assertEquals(4, privilegesDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Privileges privileges = Privileges.builder().name("newPrivilege").build();

        Privileges saved = privilegesDao.save(privileges);

        Assert.assertEquals(saved, privilegesDao.getById(saved.getId()));
    }

}