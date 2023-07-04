package com.example.orderservice.service.Impl;

import com.example.orderservice.dao.OrderRepository;
import com.example.orderservice.dto.AllOrdersForUser;
import com.example.orderservice.dto.OrdersDto;
import com.example.orderservice.mapper.OrdersMapper;
import com.example.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrdersMapper ordersMapper;

    private final OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public OrderServiceImpl(OrdersMapper dishMapper, OrderRepository dishRepository) {
        this.ordersMapper = dishMapper;
        this.orderRepository = dishRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.order}")
    public List<OrdersDto> getAll(Pageable pageable){
        return orderRepository.findAll(pageable).map(ordersMapper::mapToOrdersDTO).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.order}")
    public OrdersDto getById(Integer id){
        return orderRepository.findById(id).map(ordersMapper::mapToOrdersDTO).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.order}")
    @Transactional
    public void save(OrdersDto ordersDto){
        orderRepository.save(ordersMapper.mapToOrders(ordersDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.order}")
    @Transactional
    public void delete(Integer id){
        orderRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.order}")
    @Transactional
    public void update(OrdersDto ordersDto){
        orderRepository.save(ordersMapper.mapToOrders(ordersDto));
    }


    @RabbitListener(queues = "${rabbitmq.queue.saveorderforcart.order}")
    @Transactional
    @Override
    public void saveOrderFromCart(Integer userId) {

        Integer orderId = orderRepository.saveOrderFromCart(userId);
        orderRepository.saveOrderedDishesRelations(userId, orderId);
        orderRepository.clearCart(userId);
    }

    @RabbitListener(queues = "${rabbitmq.queue.getorderforuser.order}")
    @Override
    public List<OrdersDto> getAllOrdersForUser(AllOrdersForUser allOrdersForUser) {

        return orderRepository.getAllByUsers_Id(allOrdersForUser.getId(), allOrdersForUser.getPageable())
                .stream()
                .map(ordersMapper::mapToOrdersDTO)
                .toList();

        // return ordersDao.getAllOrdersForUserById(id, pageable).stream().map(ordersMapper::mapToOrdersDTO).toList();
    }
}
