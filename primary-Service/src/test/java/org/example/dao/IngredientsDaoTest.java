package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.IngredientsDao;
import org.example.entity.Ingredients;
import org.example.entity.MeasureUnits;
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
public class IngredientsDaoTest {
    @Autowired
    private IngredientsDao ingredientsDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Ingredients ingredients = Ingredients.builder()
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        Ingredients saveDishes = ingredientsDao.save(ingredients);

        Assert.assertEquals(saveDishes, ingredientsDao.getById(saveDishes.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Ingredients ingredients = Ingredients.builder()
//                                             .name("test")
//                                             .quantity(new BigDecimal(10))
//                                             .measureUnits(MeasureUnits.builder().id(2).build())
//                                             .build();
//
//        ingredientsDao.save(ingredients);
//
//        Assert.assertEquals(4, ingredientsDao.getAll().size());
//    }
//
//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Ingredients ingredients = Ingredients.builder()
//                                             .name("test")
//                                             .quantity(new BigDecimal(10))
//                                             .measureUnits(MeasureUnits.builder().id(2).build())
//                                             .build();
//
//        ingredientsDao.save(ingredients);
//
//        Assert.assertEquals(4, ingredientsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Ingredients ingredients = Ingredients.builder()
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        Ingredients saved = ingredientsDao.save(ingredients);

        saved.setName("test1");

        ingredientsDao.update(saved);

        Ingredients newIngredients = ingredientsDao.getById(saved.getId());
        Assert.assertEquals("test1", newIngredients.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Ingredients ingredients = Ingredients.builder()
//                                             .name("test")
//                                             .quantity(new BigDecimal(10))
//                                             .measureUnits(MeasureUnits.builder().id(2).build())
//                                             .build();
//
//        Ingredients saved = ingredientsDao.save(ingredients);
//
//        ingredientsDao.delete(saved.getId());
//
//        Assert.assertEquals(3, ingredientsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getIngredientByNameTest() throws SQLException, InterruptedException {
        Ingredients get = ingredientsDao.getIngredientByName("pepperoni");
        Assert.assertEquals(get, ingredientsDao.getById(3));
    }

    @Transactional
    @Test
    public void getIngredientsWithFetchByIdTest() throws SQLException, InterruptedException {
        Ingredients get = ingredientsDao.getIngredientsWithFetchById(1);
        Assert.assertEquals(get, ingredientsDao.getById(1));
    }
}