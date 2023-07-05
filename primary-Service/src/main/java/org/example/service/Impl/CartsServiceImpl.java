package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.*;
import org.example.dto.CartsDto;
import org.example.entities.entity.Carts;
import org.example.entities.entity.Users;
import org.example.service.CartsService;
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
public class CartsServiceImpl implements CartsService {

    @Value("${routing.key.queue.cart.delete}")
    private String cartDeleteRoutingKey;
    @Value("${routing.key.queue.cart.update}")
    private String cartUpdateRoutingKey;
    @Value("${routing.key.queue.cart.save}")
    private String cartSaveRoutingKey;
    @Value("${routing.key.queue.cart.getbyusername}")
    private String cartGetByUserNameRoutingKey;

    @Value("${cart.exchange}")
    private String cartExchange;

    private final RestTemplate restTemplate;

    private final RabbitTemplate rabbitTemplate;


    @Override
    public List<CartsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "CART" + "/carts?page={page}&size={size}",
                CartsDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));

        //return cartsDao.getAll(pageable).stream().map(cartsMapper::mapToCartsDto).toList();
    }

    @Override
    public CartsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);

        return restTemplate.getForObject("http://" + "CART" + "/carts/{id}", CartsDto.class, id);
//        Carts byId = cartsDao.getById(id);
//        return cartsMapper.mapToCartsDto(byId);
    }

    @Override
    public CartsDto getById(String userName) {
        log.debug("Executing method getById with id {}", userName);
//        Carts byId = null;
//        try {
//            byId = cartsDao.getById(userDao.getUserByMail(userName).getId());
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return cartsMapper.mapToCartsDto(byId);

        return rabbitTemplate.convertSendAndReceiveAsType(
                cartExchange,
                cartGetByUserNameRoutingKey,
                userName,
                new ParameterizedTypeReference<CartsDto>() {
                });
    }

    @Transactional
    @Override
    public CartsDto save(CartsDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
//        try {
//            return cartsMapper.mapToCartsDto(cartsDao.save(cartsMapper.mapToCarts(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return rabbitTemplate.convertSendAndReceiveAsType(
                cartExchange,
                cartSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<CartsDto>() {
                });
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
//        cartsDao.delete(id);

        rabbitTemplate.convertAndSend(cartExchange, cartDeleteRoutingKey, id);
    }

    @Transactional
    @Override
    public CartsDto update(CartsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        //return cartsMapper.mapToCartsDto(cartsDao.update(cartsMapper.mapToCarts(entityDto)));

        return rabbitTemplate.convertSendAndReceiveAsType(
                cartExchange,
                cartUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<CartsDto>() {
                });
    }

    @Transactional
    @Override
    public void saveCartAfterRegistration(Integer id) {
        log.debug("Executing method saveCartAfterRegistration with userId {}", id);
        Carts build = Carts.builder().user(Users.builder().id(id).build()).build();

        rabbitTemplate.convertAndSend(
                cartExchange,
                cartSaveRoutingKey,
                build);
    }
}
