package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.StatusDao;
import org.example.dto.StatusDto;
import org.example.entity.Status;
import org.example.mapper.StatusMapper;
import org.example.mapper.StatusMapperImpl;
import org.example.service.Impl.StatusServiceImpl;
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
public class StatusServiceTest {

    @Spy
    StatusMapper statusMapper = new StatusMapperImpl();
    @Mock
    StatusDao statusDao;
    @Spy
    @InjectMocks
    StatusService statusInterface = new StatusServiceImpl(statusMapper, statusDao);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Status status = Status.builder().id(1).status("testStatus").build();

        Mockito.when(statusDao.getById(1)).thenReturn(status);

        Assert.assertEquals("1", statusInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Status status1 = Status.builder().id(1).status("testStatus").build();
//        Status status2 = Status.builder().id(2).status("testStatus").build();
//
//        List<Status> statuses = new ArrayList<>();
//        statuses.add(status1);
//        statuses.add(status2);
//
//        Mockito.when(statusDao.getAll()).thenReturn(statuses);
//
//        Assert.assertEquals(2, statusInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Status status = Status.builder().id(1).status("testStatus").build();


        Mockito.when(statusDao.update(any())).thenReturn(status);

        var updateOutput = statusInterface.update(new StatusDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Status status = Status.builder().id(1).status("testStatus").build();


        Mockito.when(statusDao.save(any())).thenReturn(status);

        var saveOutput = statusInterface.save(new StatusDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        statusDao.delete(1);

        Mockito.verify(statusDao, Mockito.times(1)).delete(1);
    }
}
