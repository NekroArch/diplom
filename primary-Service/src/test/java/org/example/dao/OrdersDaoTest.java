package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.OrdersDao;
import org.example.entity.Orders;
import org.example.entity.Status;
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

import java.math.BigDecimal;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
public class OrdersDaoTest {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private CartsDao cartsDao;
    @Autowired
    private DishesDao dishesDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void r() throws SQLException, InterruptedException {
//        ordersDao.saveOrderFromCart(1);
//        Orders byId = ordersDao.getById(5);
//        Assert.assertEquals(new BigDecimal(102).setScale(2), byId.getPrice());
//    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Orders orders = Orders.builder()
//                              .users(Users.builder().id(1).build())
//                              .status(Status.builder().id(1).build())
//                              .price(new BigDecimal(5))
//                              .build();
//
//        ordersDao.save(orders);
//
//        Assert.assertEquals(2, ordersDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Orders orders = Orders.builder()
//                              .users(Users.builder().id(1).build())
//                              .status(Status.builder().id(1).build())
//                              .price(new BigDecimal(5))
//                              .build();
//
//        ordersDao.save(orders);
//
//
//        Assert.assertEquals(2, ordersDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Orders orders = Orders.builder()
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();

        Orders saved = ordersDao.save(orders);

        saved.setPrice(new BigDecimal(10));

        ordersDao.update(saved);

        Orders newOrder = ordersDao.getById(saved.getId());
        Assert.assertEquals(new BigDecimal(10), newOrder.getPrice());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Orders orders = Orders.builder()
//                              .users(Users.builder().id(1).build())
//                              .status(Status.builder().id(1).build())
//                              .price(new BigDecimal(5))
//                              .build();
//
//        Orders saved = ordersDao.save(orders);
//
//        ordersDao.delete(saved.getId());
//
//        Assert.assertEquals(1, ordersDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Orders orders = Orders.builder()
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();

        Orders saved = ordersDao.save(orders);

        Assert.assertEquals(saved, ordersDao.getById(saved.getId()));
    }

    @Transactional
    @Test
    public void findDishesInMenuByNameTest() throws SQLException, InterruptedException {
        Orders order = ordersDao.getOrdersWithFetchById(1);
        Assert.assertEquals(order, ordersDao.getOrdersWithFetchById(1));
    }
}