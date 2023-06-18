package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.DishesDao;
import org.example.dto.DishesDto;
import org.example.entity.Dishes;
import org.example.entity.Menu;
import org.example.mapper.DishMapper;
import org.example.mapper.DishMapperImpl;
import org.example.service.Impl.DishesServiceImpl;
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
public class DishesServiceTest {
    @Spy
    DishMapper dishMapper = new DishMapperImpl();
    @Mock
    DishesDao dishesDao;
    @InjectMocks
    DishesService dishesInterface = new DishesServiceImpl(dishesDao, dishMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Dishes dishes = Dishes.builder().id(1)
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();
        Mockito.when(dishesDao.getById(1)).thenReturn(dishes);

        Assert.assertEquals("1", dishesInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Dishes dishes1 = Dishes.builder().id(1)
//                              .name("test1")
//                              .price(new BigDecimal(5))
//                              .menu(Menu.builder().id(1).build())
//                              .build();
//        Dishes dishes2 = Dishes.builder()
//                              .name("test2").id(2)
//                              .price(new BigDecimal(5))
//                              .menu(Menu.builder().id(1).build())
//                              .build();
//
//        List<Dishes> dishes = new ArrayList<>();
//        dishes.add(dishes1);
//        dishes.add(dishes2);
//        Mockito.when(dishesDao.getAll()).thenReturn(dishes);
//
//        Assert.assertEquals(2, dishesInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Dishes dishes = Dishes.builder()
                              .id(1)
                              .name("test")
                              .price(new BigDecimal(5))
                              .build();


        Mockito.when(dishesDao.update(any(Dishes.class))).thenReturn(dishes);

        var updateOutput = dishesInterface.update(new DishesDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Dishes dishes = Dishes.builder().id(1)
                              .name("test")
                              .price(new BigDecimal(5))
                              .menu(Menu.builder().id(1).build())
                              .build();


        Mockito.when(dishesDao.save(any())).thenReturn(dishes);

        var saveOutput = dishesInterface.save(new DishesDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        dishesDao.delete(1);

        Mockito.verify(dishesDao, Mockito.times(1)).delete(1);
    }
}
