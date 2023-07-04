package org.example.cartservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.dao.CartRepository;
import org.example.cartservice.dto.CartsDto;
import org.example.cartservice.mapper.CartsMapper;
import org.example.cartservice.service.CartsService;
import org.example.entities.entity.Carts;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.Users;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartsServiceImpl implements CartsService {

    private final CartsMapper cartsMapper;

    private final CartRepository cartsDao;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${rabbitmq.queue.getall.cart}")
    @Override
    public List<CartsDto> getAll(Pageable pageable) {
        log.debug("Executing method getAll");
        return cartsDao.findAll(pageable).stream().map(cartsMapper::mapToCartsDto).toList();
    }

    @RabbitListener(queues = "${rabbitmq.queue.get.cart}")
    @Override
    public CartsDto getById(Integer id) {
        //log.debug("Executing method getById with id {}", id);
        Carts byId = cartsDao.findById(id).get();
        return cartsMapper.mapToCartsDto(byId);
    }

    @RabbitListener(queues = "${rabbitmq.queue.getbyusername.cart}")
    @Override
    public CartsDto getByUserName(String userName, String userExchange, String routingKey) {
        //log.debug("Executing method getById with id {}", userName);
        Carts byId = cartsDao.findById(
                rabbitTemplate.convertSendAndReceiveAsType(
                        userExchange,
                        routingKey,
                        userName,
                        new ParameterizedTypeReference<Users>() {
                        }).getId()
        ).get();

        return cartsMapper.mapToCartsDto(byId);
    }

    @RabbitListener(queues = "${rabbitmq.queue.save.cart}")
    @Transactional
    @Override
    public CartsDto save(CartsDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
        return cartsMapper.mapToCartsDto(cartsDao.save(cartsMapper.mapToCarts(entityDto)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.cart}")
    @Transactional
    @Override
    public void delete(Integer id) {
        // log.debug("Executing method delete with id {}", id);
        cartsDao.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.queue.update.cart}")
    @Transactional
    @Override
    public CartsDto update(CartsDto entityDto) {
        //log.debug("Executing method update with {}", entityDto);
        return cartsMapper.mapToCartsDto(cartsDao.save(cartsMapper.mapToCarts(entityDto)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.savewithuserid.cart}")
    @Transactional
    @Override
    public void saveCartAfterRegistration(Integer id) {
        //log.debug("Executing method saveCartAfterRegistration with userId {}", id);
        cartsDao.save(Carts.builder().user(Users.builder().id(id).build()).build());
    }
}
