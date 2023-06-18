package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.AddonsDao;
import org.example.dto.AddonsDto;
import org.example.entity.Addons;
import org.example.mapper.AddonsMapper;
import org.example.mapper.AddonsMapperImpl;
import org.example.service.Impl.AddonsServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
public class AddonsServiceTest {
    @Spy
    AddonsMapper addonsCategoryMapper = new AddonsMapperImpl();
    @Mock
    AddonsDao addonsDao;
    @InjectMocks
    AddonsService addonsInterface = new AddonsServiceImpl(addonsDao, addonsCategoryMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Addons addons = Addons.builder()
                              .id(1)
                              .name("name")
                              .price(new BigDecimal(12))
                              .build();
        Mockito.when(addonsDao.getById(1)).thenReturn(addons);

        Assert.assertEquals("1", addonsInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Addons addons1 = Addons.builder()
//                              .id(1)
//                              .name("name")
//                              .price(new BigDecimal(12))
//                              .build();
//        Addons addons2 = Addons.builder()
//                              .id(2)
//                              .name("name2")
//                              .price(new BigDecimal(2))
//                              .build();
//
//        List<Addons> addons = new ArrayList<>();
//        addons.add(addons1);
//        addons.add(addons2);
//
//        Mockito.when(addonsDao.getAll()).thenReturn(addons);
//
//        Assert.assertEquals(2, addonsDao.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Addons addons = Addons.builder()
                               .id(1)
                               .name("name")
                               .price(new BigDecimal(12))
                               .build();


        Mockito.when(addonsDao.update(any())).thenReturn(addons);

        var updateOutput = addonsInterface.update(new AddonsDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Addons addons = Addons.builder()
                              .id(1)
                              .name("name")
                              .price(new BigDecimal(12))
                              .build();


        Mockito.when(addonsDao.save(any())).thenReturn(addons);

        var saveOutput = addonsInterface.save(new AddonsDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        addonsDao.delete(1);

        Mockito.verify(addonsDao, Mockito.times(1)).delete(1);
    }
}
