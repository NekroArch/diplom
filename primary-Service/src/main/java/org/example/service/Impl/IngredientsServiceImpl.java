package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IngredientsDao;
import org.example.dto.AddonsDto;
import org.example.dto.DishesDto;
import org.example.dto.IngredientsDto;
import org.example.mapper.IngredientsMapper;
import org.example.service.IngredientsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {
//    private final IngredientsDao ingredientsDao;
//
//    private final IngredientsMapper ingredientsMapper;

    @Value("${routing.key.queue.ingredient.delete}")
    private String ingredientDeleteRoutingKey;
    @Value("${routing.key.queue.ingredient.update}")
    private String ingredientUpdateRoutingKey;
    @Value("${routing.key.queue.ingredient.save}")
    private String ingredientSaveRoutingKey;

    @Value("${ingredient.exchange}")
    private String ingredientExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<IngredientsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "http://" + "INGREDIENT" + "/ingredients?page={page}&size={size}",
                IngredientsDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
       // return ingredientsDao.getAll(pageable).stream().map(ingredientsMapper::mapToIngredientsDto).toList();
    }

    @Override
    public IngredientsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);

        return restTemplate.getForObject("http://"+ "INGREDIENT" + "/ingredients/{id}", IngredientsDto.class, id );
       // return ingredientsMapper.mapToIngredientsDto(ingredientsDao.getById(id));
    }
    @Transactional
    @Override
    public IngredientsDto save(IngredientsDto entityDto){
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                ingredientExchange,
                ingredientSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<IngredientsDto>() {
                });

//        try {
//            return ingredientsMapper.mapToIngredientsDto(ingredientsDao.save(ingredientsMapper.mapToIngredients(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);

        rabbitTemplate.convertAndSend(ingredientExchange, ingredientDeleteRoutingKey, id);

        //ingredientsDao.delete(id);
    }
    @Transactional
    @Override
    public IngredientsDto update(IngredientsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                ingredientExchange,
                ingredientUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<IngredientsDto>() {
                });

        //return ingredientsMapper.mapToIngredientsDto(ingredientsDao.update(ingredientsMapper.mapToIngredients(entityDto)));
    }
}
