package org.example.dao;

import org.example.TestContainersConfig;
import org.example.config.DatabaseConfig;
import org.example.dao.MeasureUnitsDao;
import org.example.entity.MeasureUnits;
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
public class MeasureUnitsDaoTest {
    @Autowired
    private MeasureUnitsDao measureUnitsDao;

    @Autowired
    @Container
    PostgreSQLContainer postgreSQLContainer;

    @Transactional
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        MeasureUnits saveMeasureUnits = measureUnitsDao.save(measureUnits);

        Assert.assertEquals(saveMeasureUnits, measureUnitsDao.getById(saveMeasureUnits.getId()));
    }

//    @Transactional
//    @Test
//    public void saveTest() throws SQLException, InterruptedException {
//        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();
//
//        measureUnitsDao.save(measureUnits);
//
//        Assert.assertEquals(4, measureUnitsDao.getAll().size());
//    }
//
//    @Transactional
//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();
//
//        measureUnitsDao.save(measureUnits);
//
//        Assert.assertEquals(4, measureUnitsDao.getAll().size());
//    }

    @Transactional
    @Test
    public void updateTest() throws SQLException, InterruptedException {
        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();

        MeasureUnits saved = measureUnitsDao.save(measureUnits);

        saved.setSiUnit("lu");

        measureUnitsDao.update(saved);

        MeasureUnits nemMeasureUnit = measureUnitsDao.getById(saved.getId());
        Assert.assertEquals("lu", nemMeasureUnit.getSiUnit());
    }

//    @Transactional
//    @Test
//    public void deleteTest() throws SQLException, InterruptedException {
//        MeasureUnits measureUnits = MeasureUnits.builder().siUnit("u").build();
//
//        MeasureUnits saved = measureUnitsDao.save(measureUnits);
//
//        measureUnitsDao.delete(saved.getId());
//
//        Assert.assertEquals(3, measureUnitsDao.getAll().size());
//    }

}
