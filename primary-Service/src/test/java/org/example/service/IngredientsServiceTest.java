package org.example.service;

import org.example.config.AppConfig;
import org.example.dao.IngredientsDao;
import org.example.dto.IngredientsDto;
import org.example.entity.Ingredients;
import org.example.entity.MeasureUnits;
import org.example.mapper.IngredientsMapper;
import org.example.mapper.IngredientsMapperImpl;
import org.example.service.Impl.IngredientsServiceImpl;
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
public class IngredientsServiceTest {
    @Spy
    IngredientsMapper ingredientsMapper = new IngredientsMapperImpl();
    @Mock
    IngredientsDao ingredientsDao;
    @Spy
    @InjectMocks
    IngredientsService ingredientsInterface = new IngredientsServiceImpl(ingredientsDao, ingredientsMapper);
    @Test
    public void getByIdTest() throws SQLException, InterruptedException {
        Ingredients ingredients = Ingredients.builder().id(1)
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();

        Mockito.when(ingredientsDao.getById(1)).thenReturn(ingredients);

        Assert.assertEquals("1", ingredientsInterface.getById(1).getId().toString());
    }

//    @Test
//    public void getAllTest() throws SQLException, InterruptedException {
//        Ingredients ingredients1 = Ingredients.builder().id(1)
//                                              .name("test1")
//                                              .quantity(new BigDecimal(10))
//                                              .measureUnits(MeasureUnits.builder().id(2).build())
//                                              .build();
//        Ingredients ingredients2 = Ingredients.builder().id(2)
//                                              .name("test2")
//                                              .quantity(new BigDecimal(10))
//                                              .measureUnits(MeasureUnits.builder().id(2).build())
//                                              .build();
//
//        List<Ingredients> ingredients = new ArrayList<>();
//        ingredients.add(ingredients1);
//        ingredients.add(ingredients2);
//
//        Mockito.when(ingredientsDao.getAll()).thenReturn(ingredients);
//
//        Assert.assertEquals(2, ingredientsInterface.getAll().size());
//    }

    @Test
    public void updateTest() throws SQLException, InterruptedException {
        Ingredients ingredients = Ingredients.builder().id(1)
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();


        Mockito.when(ingredientsDao.update(any())).thenReturn(ingredients);

        var updateOutput = ingredientsInterface.update(new IngredientsDto());

        Assert.assertEquals("1", updateOutput.getId().toString());
    }

    @Test
    public void saveTest() throws SQLException, InterruptedException {
        Ingredients ingredients = Ingredients.builder().id(1)
                                             .name("test")
                                             .quantity(new BigDecimal(10))
                                             .measureUnits(MeasureUnits.builder().id(2).build())
                                             .build();


        Mockito.when(ingredientsDao.save(any())).thenReturn(ingredients);

        var saveOutput = ingredientsInterface.save(new IngredientsDto());

        Assert.assertEquals("1", saveOutput.getId().toString());
    }

    @Test
    public void deleteTest() throws SQLException, InterruptedException {
        ingredientsDao.delete(1);

        Mockito.verify(ingredientsDao, Mockito.times(1)).delete(1);
    }
}
