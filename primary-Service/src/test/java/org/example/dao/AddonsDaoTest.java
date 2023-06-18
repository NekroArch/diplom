package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.AddonsDao;
import org.example.entity.Addons;
import org.example.entity.AddonsCategory;
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
public class AddonsDaoTest {

    @Autowired
    private AddonsDao addonsDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();

        Addons saveAddons = addonsDao.save(addons);

        Assert.assertEquals(saveAddons, addonsDao.getById(saveAddons.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Addons addons = Addons.builder()
//                              .category(AddonsCategory.builder().id(1).build())
//                              .name("addon")
//                              .price(new BigDecimal(7))
//                              .build();
//
//        addonsDao.save(addons);
//
//        Assert.assertEquals(3, addonsDao.getAll().size());
//    }

//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Addons addons = Addons.builder()
//                              .category(AddonsCategory.builder().id(1).build())
//                              .name("addon")
//                              .price(new BigDecimal(7))
//                              .build();
//
//        addonsDao.save(addons);
//
//        Assert.assertEquals(3, addonsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Addons addons = Addons.builder()
                              .category(AddonsCategory.builder().id(1).build())
                              .name("addon")
                              .price(new BigDecimal(7))
                              .build();

        Addons saved = addonsDao.save(addons);

        saved.setName("addons");

        addonsDao.update(saved);

        Addons newAddons = addonsDao.getById(saved.getId());
        Assert.assertEquals("addons", newAddons.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Addons addons = Addons.builder()
//                              .category(AddonsCategory.builder().id(1).build())
//                              .name("addon")
//                              .price(new BigDecimal(7))
//                              .build();
//
//        Addons saved = addonsDao.save(addons);
//
//        addonsDao.delete(saved.getId());
//
//        Assert.assertEquals(2, addonsDao.getAll().size());
//    }
    @Transactional
    @Test
    public void getByPrice() {
        Assert.assertEquals(2, addonsDao.findAddonsByPrice(new BigDecimal(6)).size());
    }
}