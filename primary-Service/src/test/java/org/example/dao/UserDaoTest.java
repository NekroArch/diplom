package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.UserDao;
import org.example.entity.Users;
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
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        Users saveDishes = userDao.save(user);

        Assert.assertEquals(saveDishes, userDao.getById(saveDishes.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Users user = Users.builder()
//                          .firstName("firstname")
//                          .lastName("lastname")
//                          .mail("31323@gmail.com")
//                          .phone("32234553")
//                          .password("24342523")
//                          .build();
//
//        userDao.save(user);
//
//        Assert.assertEquals(3, userDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Users user = Users.builder()
//                          .firstName("firstname")
//                          .lastName("lastname")
//                          .mail("31323@gmail.com")
//                          .phone("32234553")
//                          .password("24342523")
//                          .build();
//
//        userDao.save(user);
//
//        Assert.assertEquals(3, userDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Users user = Users.builder()
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();

        Users saved = userDao.save(user);

        saved.setFirstName("test1");

        userDao.update(saved);

        Users newUser = userDao.getById(saved.getId());
        Assert.assertEquals("test1", newUser.getFirstName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Users user = Users.builder()
//                          .firstName("firstname")
//                          .lastName("lastname")
//                          .mail("31323@gmail.com")
//                          .phone("32234553")
//                          .password("24342523")
//                          .build();
//
//        Users saved = userDao.save(user);
//
//        userDao.delete(saved.getId());
//
//        Assert.assertEquals(2, userDao.getAll().size());
//    }


}