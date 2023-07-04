package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrdersDto;
import org.example.service.Impl.SendType.AllOrdersForUser;
import org.example.service.OrdersService;
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
public class OrdersServiceImpl implements OrdersService {

    @Value("${routing.key.queue.order.delete}")
    private String ingredientDeleteRoutingKey;
    @Value("${routing.key.queue.order.update}")
    private String ingredientUpdateRoutingKey;
    @Value("${routing.key.queue.order.save}")
    private String ingredientSaveRoutingKey;
    @Value("${routing.key.queue.order.saveorderforcart}")
    private String orderSaveOrderForCartRoutingKey;
    @Value("${routing.key.queue.order.getorderforuser}")
    private String orderGetOrderForUserRoutingKey;

    @Value("${order.exchange}")
    private String orderExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<OrdersDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "ORDER" + "/orders?page={page}&size={size}",
                OrdersDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));

        // return ordersDao.getAll(pageable).stream().map(ordersMapper::mapToOrdersDTO).toList();
    }

    @Override
    public OrdersDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        // return ordersMapper.mapToOrdersDTO(ordersDao.getById(id));

        OrdersDto forObject = restTemplate.getForObject("http://" + "ORDER" + "/orders/{id}", OrdersDto.class, id);
        return forObject;
    }

    @Transactional
    @Override
    public OrdersDto save(OrdersDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
//        try {
//            return ordersMapper.mapToOrdersDTO(ordersDao.save(ordersMapper.mapToOrders(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return rabbitTemplate.convertSendAndReceiveAsType(
                orderExchange,
                ingredientSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<OrdersDto>() {
                }
        );
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(
                orderExchange,
                ingredientDeleteRoutingKey,
                id);
    }

    @Transactional
    @Override
    public OrdersDto update(OrdersDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                orderExchange,
                ingredientUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<OrdersDto>() {
                }
        );
    }

    @Transactional
    @Override
    public void saveOrderFromCart(Integer userId) {
        log.debug("Executing method saveOrderFromCart with userId {}", userId);

        rabbitTemplate.convertAndSend(
                orderExchange,
                orderSaveOrderForCartRoutingKey,
                userId
        );
    }

    @Override
    public List<OrdersDto> getAllOrdersForUser(Integer id, Pageable pageable) {
        log.debug("Executing method saveOrderFromCart with userId {}", id);

        AllOrdersForUser allOrdersForUser = AllOrdersForUser.builder().id(id).pageable(pageable).build();

        return rabbitTemplate.convertSendAndReceiveAsType(
                orderExchange,
                orderGetOrderForUserRoutingKey,
                allOrdersForUser,
                new ParameterizedTypeReference<List<OrdersDto>>() {
                }
        );

       // return ordersDao.getAllOrdersForUserById(id, pageable).stream().map(ordersMapper::mapToOrdersDTO).toList();
    }
}
