package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.CartsDao;
import org.example.entity.*;
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
public class CartsDaoTest {

    @Autowired
    private CartsDao cartsDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Carts cart = Carts.builder()
//                          .user(Users.builder().id(2).build())
//                          .build();
//
//        cartsDao.save(cart);
//
//        Assert.assertEquals(2, cartsDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Carts cart = Carts.builder()
//                          .user(Users.builder().id(2).build())
//                          .build();
//
//        cartsDao.save(cart);
//
//
//        Assert.assertEquals(2, cartsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Carts cart = Carts.builder()
                          .user(Users.builder().id(2).build())
                          .build();

        Carts saved = cartsDao.save(cart);

        saved.getUser().setId(3);

        cartsDao.update(saved);

        Carts newCart = cartsDao.getById(saved.getId());
        Assert.assertEquals("3", newCart.getUser().getId().toString());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Carts cart = Carts.builder()
//                          .user(Users.builder().id(2).build())
//                          .build();
//
//        Carts saved = cartsDao.save(cart);
//
//        cartsDao.delete(saved.getId());
//
//        Assert.assertEquals(1, cartsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Carts cart = Carts.builder()
                          .user(Users.builder().id(2).build())
                          .build();

        Carts saved = cartsDao.save(cart);

        Assert.assertEquals(saved, cartsDao.getById(saved.getId()));
    }

//    @Transactional
//    @Test
//    public void getDishInCartsWithFetchByIdTest() throws SQLException, InterruptedException {
//        Carts carts = cartsDao.getDishInCartsWithFetchById(1);
//        Assert.assertEquals(carts, cartsDao.getById(carts.getId()));
//    }
}