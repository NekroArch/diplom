package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CartsDao;
import org.example.dao.OrdersDao;
import org.example.dto.OrdersDto;
import org.example.dto.Pageable;
import org.example.mapper.OrdersMapper;
import org.example.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersMapper ordersMapper;

    private final OrdersDao ordersDao;

    private final CartsDao cartsDao;

    @Override
    public List<OrdersDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return ordersDao.getAll(pageable).stream().map(ordersMapper::mapToOrdersDTO).toList();
    }

    @Override
    public OrdersDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return ordersMapper.mapToOrdersDTO(ordersDao.getById(id));
    }
    @Transactional
    @Override
    public OrdersDto save(OrdersDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);
        return ordersMapper.mapToOrdersDTO(ordersDao.save(ordersMapper.mapToOrders(entityDto)));
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        ordersDao.delete(id);
    }
    @Transactional
    @Override
    public OrdersDto update(OrdersDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return ordersMapper.mapToOrdersDTO(ordersDao.update(ordersMapper.mapToOrders(entityDto)));
    }

    @Transactional
    @Override
    public void saveOrderFromCart(Integer userId){
        log.debug("Executing method saveOrderFromCart with userId {}", userId);

        Integer orderId = ordersDao.saveOrderFromCart(userId);
        ordersDao.saveOrderedDishesRelations(userId, orderId);
        cartsDao.clearCardByUserId(userId);
    }

    @Override
    public List<OrdersDto> getAllOrdersForUser(Integer id, Pageable pageable) {
        log.debug("Executing method saveOrderFromCart with userId {}", id);

        return ordersDao.getAllOrdersForUserById(id, pageable).stream().map(ordersMapper::mapToOrdersDTO).toList();
    }
}
