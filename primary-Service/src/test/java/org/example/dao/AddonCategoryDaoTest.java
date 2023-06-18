package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.AddonsCategoryDao;
import org.example.dto.Pageable;
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

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
public class AddonCategoryDaoTest {
    @Autowired
    private AddonsCategoryDao addonsCategoryDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        AddonsCategory addonsCategory = AddonsCategory.builder()
                                                      .name("sweet")
                                                      .build();

        AddonsCategory saveAddonsCategory = addonsCategoryDao.save(addonsCategory);

        Assert.assertEquals(saveAddonsCategory, addonsCategoryDao.getById(saveAddonsCategory.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        AddonsCategory addonsCategory = AddonsCategory.builder()
//                                                      .name("sweet")
//                                                      .build();
//
//        addonsCategoryDao.save(addonsCategory);
//
//        Assert.assertEquals(3, addonsCategoryDao.getAll().size());
//    }
//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        AddonsCategory addonsCategory = AddonsCategory.builder()
//                                                      .name("sweet1")
//                                                      .build();
//        AddonsCategory addonsCategory2 = AddonsCategory.builder()
//                                                       .name("sweet2")
//                                                       .build();
//        AddonsCategory addonsCategory3 = AddonsCategory.builder()
//                                                       .name("sweet3")
//                                                       .build();
//
//        addonsCategoryDao.save(addonsCategory);
//        addonsCategoryDao.save(addonsCategory2);
//        addonsCategoryDao.save(addonsCategory3);
//
//        Assert.assertEquals(5, addonsCategoryDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        AddonsCategory genre = AddonsCategory.builder()
                                             .name("sweet")
                                             .build();

        AddonsCategory saved = addonsCategoryDao.save(genre);

        saved.setName("sweet1");

        addonsCategoryDao.update(saved);

        AddonsCategory newAddonsCategory = addonsCategoryDao.getById(saved.getId());
        Assert.assertEquals("sweet1", newAddonsCategory.getName());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        AddonsCategory addonsCategory = AddonsCategory.builder()
//                                                      .name("sweet")
//                                                      .build();
//
//        AddonsCategory saved = addonsCategoryDao.save(addonsCategory);
//
//        addonsCategoryDao.delete(saved.getId());
//
//        Assert.assertEquals(2, addonsCategoryDao.getAll().size());
//    }
    @Transactional
    @Test
    public void getByName(){
        Assert.assertEquals(1, addonsCategoryDao.getAddonsInCategoryByCategoryName("spicy").getAddonsList().size());
    }
}