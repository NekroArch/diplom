package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.JdbcDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class, TestContainersConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@SpringBootTest
//@Testcontainers
public class JdbcDaoTest {

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Test
    public void JdbcTemplateQueryTest() {

        String mailFormUserWithJDBCTemplateById = jdbcDao.getMailFormUserWithJDBCTemplateById(1);

        Assert.assertEquals("admin@gmail.com", mailFormUserWithJDBCTemplateById);

    }


    @Test
    public void JdbcQueryTest() {

        String mailFormUserWithJDBCById = jdbcDao.getMailFormUserWithJDBCById(1);

        Assert.assertEquals("admin@gmail.com", mailFormUserWithJDBCById);
    }
}
