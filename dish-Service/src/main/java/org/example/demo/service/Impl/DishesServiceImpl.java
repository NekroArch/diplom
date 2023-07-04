package org.example.demo.service.Impl;

import org.example.demo.dao.DishRepository;
import org.example.demo.dto.DishesDto;
import org.example.demo.mapper.DishMapper;
import org.example.demo.service.DishesService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishesServiceImpl implements DishesService {

    private final DishMapper dishMapper;

    private final DishRepository dishRepository;

    @Autowired
    public DishesServiceImpl(DishMapper dishMapper, DishRepository dishRepository) {
        this.dishMapper = dishMapper;
        this.dishRepository = dishRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.dish}")
    public List<DishesDto> getAll(Pageable pageable){
        return dishRepository.findAll(pageable).map(dishMapper::mapToDishesDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.dish}")
    public DishesDto getById(Integer id){
        return dishRepository.findById(id).map(dishMapper::mapToDishesDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.dish}")
    @Transactional
    public void save(DishesDto dishesDto){
       dishRepository.save(dishMapper.mapToDishes(dishesDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.dish}")
    @Transactional
    public void delete(Integer id){
        dishRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.dish}")
    @Transactional
    public void update(DishesDto dishesDto){
        dishRepository.save(dishMapper.mapToDishes(dishesDto));
    }
}
