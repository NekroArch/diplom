package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.AddonsDao;
import org.example.dao.CartsDao;
import org.example.dao.DishesDao;
import org.example.dao.UserDao;
import org.example.dto.CartsDto;
import org.example.entity.Carts;
import org.example.entity.Users;
import org.example.mapper.CartsMapper;
import org.example.mapper.CartsMapperImpl;
import org.example.service.Impl.CartsServiceImpl;
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

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
public class CartsServiceTest {


    @Spy
    CartsMapper cartsMapper = new CartsMapperImpl();
    @Mock
    CartsDao cartsDao;
    @Mock
    UserDao userDao;

    @Spy
    @InjectMocks
    CartsService cartsInterface = new CartsServiceImpl(cartsMapper, cartsDao, userDao);


    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Carts cart = Carts.builder().id(1)
                .user(Users.builder().id(1).build())
                .build();
        Mockito.when(cartsDao.getById(1)).thenReturn(cart);

        Assert.assertEquals("1", cartsInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Carts cart1 = Carts.builder().id(1)
//                          .user(Users.builder().id(1).build())
//                          .build();
//        Carts cart2 = Carts.builder().id(2)
//                          .user(Users.builder().id(2).build())
//                          .build();
//
//        List<Carts> carts = new ArrayList<>();
//        carts.add(cart1);
//        carts.add(cart2);
//        Mockito.when(cartsDao.getAll()).thenReturn(carts);
//
//        Assert.assertEquals(2, cartsDao.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Carts cart = Carts.builder().id(1)
                .user(Users.builder().id(1).build())
                .build();


        Mockito.when(cartsDao.update(any())).thenReturn(cart);

        var updateOutput = cartsDao.update(new Carts());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Carts cart = Carts.builder().id(1)
                .user(Users.builder().id(1).build())
                .build();


        Mockito.when(cartsDao.save(any())).thenReturn(cart);

        var saveOutput = cartsInterface.save(new CartsDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        cartsDao.delete(1);

        Mockito.verify(cartsDao, Mockito.times(1)).delete(1);
    }
}
