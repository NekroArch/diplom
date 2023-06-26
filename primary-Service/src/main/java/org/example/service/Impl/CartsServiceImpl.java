package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.*;
import org.example.dto.CartsDto;
import org.example.entities.entity.Carts;
import org.example.entities.entity.Users;
import org.example.mapper.CartsMapper;
import org.example.service.CartsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartsServiceImpl implements CartsService {

    private final CartsMapper cartsMapper;

    private final CartsDao cartsDao;

    private final UserDao userDao;


    @Override
    public List<CartsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return cartsDao.getAll(pageable).stream().map(cartsMapper::mapToCartsDto).toList();
    }

    @Override
    public CartsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        Carts byId = cartsDao.getById(id);
        return cartsMapper.mapToCartsDto(byId);
    }

    @Override
    public CartsDto getById(String userName) {
        log.debug("Executing method getById with id {}", userName);
        Carts byId = null;
        try {
            byId = cartsDao.getById(userDao.getUserByMail(userName).getId());
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
        return cartsMapper.mapToCartsDto(byId);
    }

    @Transactional
    @Override
    public CartsDto save(CartsDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return cartsMapper.mapToCartsDto(cartsDao.save(cartsMapper.mapToCarts(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        cartsDao.delete(id);
    }

    @Transactional
    @Override
    public CartsDto update(CartsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return cartsMapper.mapToCartsDto(cartsDao.update(cartsMapper.mapToCarts(entityDto)));
    }

    @Transactional
    @Override
    public void saveCartAfterRegistration(Integer id) {
        log.debug("Executing method saveCartAfterRegistration with userId {}", id);
        try {
            cartsDao.save(Carts.builder().user(Users.builder().id(id).build()).build());
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
