package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DishesDto;
import org.example.service.DishesService;
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
public class DishesServiceImpl implements DishesService {

    @Value("${routing.key.queue.dish.delete}")
    private String dishDeleteRoutingKey;
    @Value("${routing.key.queue.dish.update}")
    private String dishUpdateRoutingKey;
    @Value("${routing.key.queue.dish.save}")
    private String dishSaveRoutingKey;

    @Value("${dish.exchange}")
    private String dishExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<DishesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "DISH" + "/dishes?page={page}&size={size}",
                DishesDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
       // return dishesDao.getAll(pageable).stream().map(dishMapper::mapToDishesDto).collect(Collectors.toList());
    }

    @Override
    public DishesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);

        return restTemplate.getForObject("http://"+ "DISH" + "/dishes/{id}", DishesDto.class, id );
        //return dishMapper.mapToDishesDto(dishesDao.getById(id));
    }

    @Transactional
    @Override
    public DishesDto save(DishesDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                dishExchange,
                dishSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<DishesDto>() {
                });

        //return dishMapper.mapToDishesDto(dishesDao.save(dishMapper.mapToDishes(entityDto)));
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(dishExchange, dishDeleteRoutingKey, id);
       // dishesDao.delete(id);
    }

    @Transactional
    @Override
    public DishesDto update(DishesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                dishExchange,
                dishUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<DishesDto>() {
                });


        // return dishMapper.mapToDishesDto(dishesDao.update(dishMapper.mapToDishes(entityDto)));
    }
}
