package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.UserDao;
import org.example.dto.UsersDto;
import org.example.entity.Users;
import org.example.mapper.UserMapper;
import org.example.mapper.UserMapperImpl;
import org.example.service.Impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
public class UserServiceTest {

    @Spy
    UserMapper userMapper = new UserMapperImpl();
    @Mock
    UserDao userDao;
    @Mock
    PasswordEncoder passwordEncoder;
    @Spy
    @InjectMocks
    UserService userInterface = new UserServiceImpl(userMapper, userDao, passwordEncoder);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Users user = Users.builder().id(1)
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();
        Mockito.when(userDao.getById(1)).thenReturn(user);

        Assert.assertEquals("1", userInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Users user1 = Users.builder().id(1)
//                          .firstName("firstname")
//                          .lastName("lastname")
//                          .mail("31323@gmail.com")
//                          .phone("32234553")
//                          .password("24342523")
//                          .build();
//        Users user2 = Users.builder().id(1)
//                          .firstName("firstname")
//                          .lastName("lastname")
//                          .mail("31323@gmail.com")
//                          .phone("32234553")
//                          .password("24342523")
//                          .build();
//
//        List<Users> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//
//        Mockito.when(userDao.getAll()).thenReturn(users);
//
//        Assert.assertEquals(2, userInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Users user = Users.builder().id(1)
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();


        Mockito.when(userDao.update(any())).thenReturn(user);

        var updateOutput = userInterface.update(new UsersDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Users user = Users.builder().id(1)
                          .firstName("firstname")
                          .lastName("lastname")
                          .mail("31323@gmail.com")
                          .phone("32234553")
                          .password("24342523")
                          .build();


        Mockito.when(userDao.save(any())).thenReturn(user);

        var saveOutput = userInterface.save(new UsersDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        userDao.delete(1);

        Mockito.verify(userDao, Mockito.times(1)).delete(1);
    }
}
