package com.example.statusservice.service.Impl;

import com.example.statusservice.dao.StatusRepository;
import com.example.statusservice.dto.StatusDto;
import com.example.statusservice.mapper.StatusMapper;
import com.example.statusservice.service.StatusService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.status}")
    public List<StatusDto> getAll(Pageable pageable) {
        return statusRepository.findAll(pageable).map(statusMapper::mapToStatusDto).toList();
    }

    @RabbitListener(queues = "${rabbitmq.queue.get.status}")
    @Override
    public StatusDto getById(Integer id) {
        return statusMapper.mapToStatusDto(statusRepository.findById(id).get());
    }

    @RabbitListener(queues = "${rabbitmq.queue.save.status}")
    @Override
    @Transactional
    public void save(StatusDto addonDto) {
        statusRepository.save(statusMapper.mapToStatus(addonDto));
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.status}")
    @Override
    @Transactional
    public void delete(Integer id) {
        statusRepository.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.queue.update.status}")
    @Override
    @Transactional
    public void update(StatusDto addonDto) {
        statusRepository.save(statusMapper.mapToStatus(addonDto));
    }
}
