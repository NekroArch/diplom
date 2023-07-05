package com.example.measureunitservice.service.Impl;

import com.example.measureunitservice.dao.MeasureUnitsRepository;
import com.example.measureunitservice.dto.MeasureUnitsDto;
import com.example.measureunitservice.mapper.MeasureUnitsMapper;
import com.example.measureunitservice.service.MeasureUnitsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasureUnitsServiceImpl implements MeasureUnitsService {

    private final MeasureUnitsMapper measureUnitsMapper;

    private final MeasureUnitsRepository measureUnitsRepository;

    @Autowired
    public MeasureUnitsServiceImpl(MeasureUnitsMapper measureUnitsMapper, MeasureUnitsRepository measureUnitsRepository) {
        this.measureUnitsMapper = measureUnitsMapper;
        this.measureUnitsRepository = measureUnitsRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.measureunit}")
    public List<MeasureUnitsDto> getAll(Pageable pageable){
        return measureUnitsRepository.findAll(pageable).map(measureUnitsMapper::mapToMeasureUnitsDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.measureunit}")
    public MeasureUnitsDto getById(Integer id){
        return measureUnitsRepository.findById(id).map(measureUnitsMapper::mapToMeasureUnitsDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.measureunit}")
    @Transactional
    public void save(MeasureUnitsDto ingredientsDto){
        measureUnitsRepository.save(measureUnitsMapper.mapToMeasureUnits(ingredientsDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.measureunit}")
    @Transactional
    public void delete(Integer id){
        measureUnitsRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.measureunit}")
    @Transactional
    public void update(MeasureUnitsDto ingredientsDto){
        measureUnitsRepository.save(measureUnitsMapper.mapToMeasureUnits(ingredientsDto));
    }
}
