package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.MeasureUnitsDao;
import org.example.dto.MeasureUnitsDto;
import org.example.entity.MeasureUnits;
import org.example.mapper.MeasureUnitsMapper;
import org.example.mapper.MeasureUnitsMapperImpl;
import org.example.service.Impl.MeasureUnitsServiceImpl;
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
public class MeasureUnitsServiceTest {

    @Spy
    MeasureUnitsMapper measureUnitsMapper = new MeasureUnitsMapperImpl();
    @Mock
    MeasureUnitsDao measureUnitsDao;
    @Spy
    @InjectMocks
    MeasureUnitsService measureUnitsInterface = new MeasureUnitsServiceImpl(measureUnitsDao, measureUnitsMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        MeasureUnits measureUnits = MeasureUnits.builder().id(1).siUnit("u").build();

        Mockito.when(measureUnitsDao.getById(1)).thenReturn(measureUnits);

        Assert.assertEquals("1", measureUnitsInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        MeasureUnits measureUnits1 = MeasureUnits.builder().id(1).siUnit("u").build();
//        MeasureUnits measureUnits2 = MeasureUnits.builder().id(2).siUnit("g").build();
//
//        List<MeasureUnits> measureUnits = new ArrayList<>();
//        measureUnits.add(measureUnits1);
//        measureUnits.add(measureUnits2);
//
//        Mockito.when(measureUnitsDao.getAll()).thenReturn(measureUnits);
//
//        Assert.assertEquals(2, measureUnitsInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        MeasureUnits measureUnits = MeasureUnits.builder().id(1).siUnit("u").build();


        Mockito.when(measureUnitsDao.update(any())).thenReturn(measureUnits);

        var updateOutput = measureUnitsInterface.update(new MeasureUnitsDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        MeasureUnits measureUnits = MeasureUnits.builder().id(1).siUnit("u").build();


        Mockito.when(measureUnitsDao.save(any())).thenReturn(measureUnits);

        var saveOutput = measureUnitsInterface.save(new MeasureUnitsDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        measureUnitsDao.delete(1);

        Mockito.verify(measureUnitsDao, Mockito.times(1)).delete(1);
    }
}
