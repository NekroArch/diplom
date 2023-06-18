package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.PrivilegesDao;
import org.example.dto.PrivilegesDto;
import org.example.entity.Privileges;
import org.example.mapper.PrivilegesMapper;
import org.example.mapper.PrivilegesMapperImpl;
import org.example.service.Impl.PrivilegesServiceImpl;
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
public class PrivilegesServiceTest {

    @Spy
    PrivilegesMapper privilegesMapper = new PrivilegesMapperImpl();
    @Mock
    PrivilegesDao privilegesDao;

    @Spy
    @InjectMocks
    PrivilegesService privilegesInterface = new PrivilegesServiceImpl(privilegesDao, privilegesMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Privileges privileges = Privileges.builder().id(1).name("newPrivilege").build();

        Mockito.when(privilegesDao.getById(1)).thenReturn(privileges);

        Assert.assertEquals("1", privilegesInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Privileges privileges1 = Privileges.builder().id(1).name("newPrivilege").build();
//        Privileges privileges2 = Privileges.builder().id(2).name("newPrivilege").build();
//
//        List<Privileges> privileges = new ArrayList<>();
//        privileges.add(privileges1);
//        privileges.add(privileges2);
//
//        Mockito.when(privilegesDao.getAll()).thenReturn(privileges);
//
//        Assert.assertEquals(2, privilegesInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Privileges privileges = Privileges.builder().id(1).name("newPrivilege").build();


        Mockito.when(privilegesDao.update(any())).thenReturn(privileges);

        var updateOutput = privilegesInterface.update(new PrivilegesDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Privileges privileges = Privileges.builder().id(1).name("newPrivilege").build();


        Mockito.when(privilegesDao.save(any())).thenReturn(privileges);

        var saveOutput = privilegesInterface.save(new PrivilegesDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        privilegesDao.delete(1);

        Mockito.verify(privilegesDao, Mockito.times(1)).delete(1);
    }
}
