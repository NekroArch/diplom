package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.MenuDao;
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

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
public class MenuDaoTest {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Menu menu = Menu.builder()
//                        .name("Menu")
//                        .build();
//
//        menuDao.save(menu);
//
//        Assert.assertEquals(4, menuDao.getAll().size());
//    }
//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Menu menu1 = Menu.builder()
//                         .name("Menu1")
//                         .build();
//        Menu menu2 = Menu.builder()
//                         .name("Menu2")
//                         .build();
//        Menu menu3 = Menu.builder()
//                         .name("Menu3")
//                         .build();
//
//        menuDao.save(menu1);
//        menuDao.save(menu2);
//        menuDao.save(menu3);
//
//        Assert.assertEquals(6, menuDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Menu genre = Menu.builder()
                         .name("Menu")
                         .build();

        Menu saved = menuDao.save(genre);

        saved.setName("Menu1");

        menuDao.update(saved);

        Menu newMenu = menuDao.getById(saved.getId());
        Assert.assertEquals("Menu1", newMenu.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Menu genre = Menu.builder()
//                         .name("Menu")
//                         .build();
//
//        Menu saved = menuDao.save(genre);
//
//        menuDao.delete(saved.getId());
//
//        Assert.assertEquals(3, menuDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Menu genre = Menu.builder()
                         .name("Menu")
                         .build();

        Menu saved = menuDao.save(genre);

        Assert.assertEquals(saved, menuDao.getById(saved.getId()));
    }

    @Transactional
    @Test
    public void findDishesInMenuByNameTest() throws SQLException, InterruptedException {
        Assert.assertEquals(1, menuDao.findDishesInMenuByName("meat").getDishes().size());
    }
}