package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.StatusDao;
import org.example.entity.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
public class StatusDaoTest {

    @Autowired
    private StatusDao statusDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        Status status = Status.builder().status("testStatus").build();
//
//        statusDao.save(status);
//
//        Assert.assertEquals(3, statusDao.getAll().size());
//    }
//
//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Status status = Status.builder().status("testStatus").build();
//
//        statusDao.save(status);
//
//
//        Assert.assertEquals(3, statusDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Status status = Status.builder().status("testStatus").build();

        Status saved = statusDao.save(status);

        saved.setStatus("newStatus");

        statusDao.update(saved);

        Status newStatus = statusDao.getById(saved.getId());
        Assert.assertEquals("newStatus", newStatus.getStatus());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        Status status = Status.builder().status("testStatus").build();
//
//        Status saved = statusDao.save(status);
//
//        statusDao.delete(saved.getId());
//
//        Assert.assertEquals(2, statusDao.getAll().size());
//    }

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Status status = Status.builder().status("testStatus").build();

        Status saved = statusDao.save(status);

        Assert.assertEquals(saved, statusDao.getById(saved.getId()));
    }
}