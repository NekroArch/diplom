package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.RolesDao;
import org.example.dto.RolesDto;
import org.example.entity.Roles;
import org.example.mapper.RolesMapper;
import org.example.mapper.RolesMapperImpl;
import org.example.service.Impl.RolesServiceImpl;
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
public class RoleServiceTest {

    @Spy
    RolesMapper rolesMapper = new RolesMapperImpl();
    @Mock
    RolesDao rolesDao;

    @Spy
    @InjectMocks
    RolesService rolesInterface = new RolesServiceImpl(rolesDao, rolesMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Roles roles = Roles.builder().id(1).name("newRole").build();

        Mockito.when(rolesDao.getById(1)).thenReturn(roles);

        Assert.assertEquals("1", rolesInterface.getById(1).getId().toString());
    }
//
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Roles roles1 = Roles.builder().id(1).name("newRole").build();
//        Roles roles2 = Roles.builder().id(2).name("newRole").build();
//
//        List<Roles> roles = new ArrayList<>();
//        roles.add(roles1);
//        roles.add(roles2);
//        Mockito.when(rolesDao.getAll()).thenReturn(roles);
//
//        Assert.assertEquals(2, rolesInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Roles roles = Roles.builder().id(1).name("newRole").build();


        Mockito.when(rolesDao.update(any())).thenReturn(roles);

        var updateOutput = rolesInterface.update(new RolesDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Roles roles = Roles.builder().id(1).name("newRole").build();


        Mockito.when(rolesDao.save(any())).thenReturn(roles);

        var saveOutput = rolesInterface.save(new RolesDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        rolesDao.delete(1);

        Mockito.verify(rolesDao, Mockito.times(1)).delete(1);
    }
}
