package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.DishesDao;
import org.example.entity.Dishes;
import org.example.entity.Menu;
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
public class DishesDaoTest {
    @Autowired
    private DishesDao dishesDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        Dishes saveDishes= dishesDao.save(dishes);

        Assert.assertEquals(saveDishes, dishesDao.getById(saveDishes.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Dishes dishes = Dishes.builder()
//                              .name("test")
//                              .price(new BigDecimal(5))
//                              .menu(Menu.builder().id(1).build())
//                              .build();
//
//        dishesDao.save(dishes);
//
//        Assert.assertEquals(2, dishesDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Dishes dishes = Dishes.builder()
//                              .name("test")
//                              .price(new BigDecimal(5))
//                              .menu(Menu.builder().id(1).build())
//                              .build();
//
//        dishesDao.save(dishes);
//
//        Assert.assertEquals(2, dishesDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Dishes dishes = Dishes.builder()
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();

        Dishes saved = dishesDao.save(dishes);

        saved.setName("test1");

        dishesDao.update(saved);

        Dishes newDishes = dishesDao.getById(saved.getId());
        Assert.assertEquals("test1", newDishes.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Dishes dishes = Dishes.builder()
//                              .name("test")
//                              .price(new BigDecimal(5))
//                              .menu(Menu.builder().id(1).build())
//                              .build();
//
//        Dishes saved = dishesDao.save(dishes);
//
//        dishesDao.delete(saved.getId());
//
//        Assert.assertEquals(1, dishesDao.getAll().size());
//    }
}