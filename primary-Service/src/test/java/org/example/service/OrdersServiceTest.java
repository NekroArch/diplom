package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.CartsDao;
import org.example.dao.OrdersDao;
import org.example.dto.OrdersDto;
import org.example.entity.Orders;
import org.example.entity.Status;
import org.example.entity.Users;
import org.example.mapper.OrdersMapper;
import org.example.mapper.OrdersMapperImpl;
import org.example.service.Impl.OrdersServiceImpl;
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
public class OrdersServiceTest {

    @Spy
    OrdersMapper ordersMapper = new OrdersMapperImpl();
    @Mock
    OrdersDao ordersDao;
    @Mock
    CartsDao cartsDao;

    @Spy
    @InjectMocks
    OrdersService ordersInterface = new OrdersServiceImpl(ordersMapper, ordersDao, cartsDao);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Orders orders = Orders.builder().id(1)
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();
        Mockito.when(ordersDao.getById(1)).thenReturn(orders);

        Assert.assertEquals("1", ordersInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Orders orders1 = Orders.builder().id(1)
//                              .users(Users.builder().id(1).build())
//                              .status(Status.builder().id(1).build())
//                              .price(new BigDecimal(5))
//                              .build();
//        Orders orders2 = Orders.builder().id(2)
//                              .users(Users.builder().id(2).build())
//                              .status(Status.builder().id(2).build())
//                              .price(new BigDecimal(5))
//                              .build();
//
//        List<Orders> orders = new ArrayList<>();
//        orders.add(orders1);
//        orders.add(orders2);
//        Mockito.when(ordersDao.getAll()).thenReturn(orders);
//
//        Assert.assertEquals(2, ordersInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Orders orders = Orders.builder().id(1)
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();


        Mockito.when(ordersDao.update(any())).thenReturn(orders);

        var updateOutput = ordersInterface.update(new OrdersDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Orders orders = Orders.builder().id(1)
                              .users(Users.builder().id(1).build())
                              .status(Status.builder().id(1).build())
                              .price(new BigDecimal(5))
                              .build();


        Mockito.when(ordersDao.save(any())).thenReturn(orders);

        var saveOutput = ordersInterface.save(new OrdersDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        ordersDao.delete(1);

        Mockito.verify(ordersDao, Mockito.times(1)).delete(1);
    }
}
