package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.MenuDao;
import org.example.dto.MenuDto;
import org.example.entity.Menu;
import org.example.mapper.MenuMapper;
import org.example.mapper.MenuMapperImpl;
import org.example.service.Impl.MenuServiceImpl;
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
public class MenuServiceTest {

    @Spy
    MenuMapper menuMapper = new MenuMapperImpl();
    @Mock
    MenuDao menuDao;
    @Spy
    @InjectMocks
    MenuService menuInterface = new MenuServiceImpl(menuDao, menuMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Menu menu = Menu.builder().id(1)
                         .name("Menu")
                         .build();
        Mockito.when(menuDao.getById(1)).thenReturn(menu);

        Assert.assertEquals("1", menuInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Menu menu1 = Menu.builder().id(1)
//                        .name("Menu1")
//                        .build();
//        Menu menu2 = Menu.builder().id(2)
//                        .name("Menu2")
//                        .build();
//
//        List<Menu> menus = new ArrayList<>();
//        menus.add(menu1);
//        menus.add(menu2);
//        Mockito.when(menuDao.getAll()).thenReturn(menus);
//
//        Assert.assertEquals(2, menuInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Menu menu = Menu.builder().id(1)
                        .name("Menu")
                        .build();


        Mockito.when(menuDao.update(any())).thenReturn(menu);

        var updateOutput = menuInterface.update(new MenuDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Menu menu = Menu.builder().id(1)
                        .name("Menu")
                        .build();


        Mockito.when(menuDao.save(any())).thenReturn(menu);

        var saveOutput = menuInterface.save(new MenuDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        menuDao.delete(1);

        Mockito.verify(menuDao, Mockito.times(1)).delete(1);
    }
}
