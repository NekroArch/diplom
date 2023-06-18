package org.example.service;

import org.example.TestContainersConfig;
import org.example.config.AppConfig;
import org.example.dao.AddonsCategoryDao;
import org.example.dto.AddonsCategoryDto;
import org.example.entity.AddonsCategory;
import org.example.mapper.AddonsCategoryMapper;
import org.example.mapper.AddonsCategoryMapperImpl;
import org.example.service.Impl.AddonsCategoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(
        classes = {AppConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
public class AddonsCategoryServiceTest {
    @Spy
    AddonsCategoryMapper addonsCategoryMapper = new AddonsCategoryMapperImpl();
    @Mock
    AddonsCategoryDao addonsCategoryDao;

//    @Autowired
//    @Container
//    PostgreSQLContainer postgreSQLContainer;
    @InjectMocks
    AddonsCategoryService addonsCategoryInterface = new AddonsCategoryServiceImpl(addonsCategoryDao, addonsCategoryMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        AddonsCategory addonsCategory = AddonsCategory.builder()
                                                      .id(1)
                                                      .name("sweet")
                                                      .build();
        Mockito.when(addonsCategoryDao.getById(1)).thenReturn(addonsCategory);

        Assert.assertEquals("1", addonsCategoryInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        AddonsCategory addonsCategory1 = AddonsCategory.builder()
//                                                      .id(1)
//                                                      .name("sweet")
//                                                      .build();
//        AddonsCategory addonsCategory2 = AddonsCategory.builder()
//                                                      .id(1)
//                                                      .name("spice")
//                                                      .build();
//
//        List<AddonsCategory> addonsCategories = new ArrayList<>();
//        addonsCategories.add(addonsCategory1);
//        addonsCategories.add(addonsCategory2);
//        Mockito.when(addonsCategoryDao.getAll()).thenReturn(addonsCategories);
//
//        Assert.assertEquals(2, addonsCategoryInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        AddonsCategory addonsCategory = AddonsCategory.builder()
                                                       .id(1)
                                                       .name("sweet")
                                                       .build();


        Mockito.when(addonsCategoryDao.update(any(AddonsCategory.class))).thenReturn(addonsCategory);

        var updateOutput = addonsCategoryInterface.update(new AddonsCategoryDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        AddonsCategory addonsCategory = AddonsCategory.builder()
                                                       .id(1)
                                                       .name("sweet")
                                                       .build();


        Mockito.when(addonsCategoryDao.save(any())).thenReturn(addonsCategory);

        var saveOutput = addonsCategoryInterface.save(new AddonsCategoryDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        addonsCategoryInterface.delete(1);

        Mockito.verify(addonsCategoryDao, Mockito.times(1)).delete(1);
    }

}
