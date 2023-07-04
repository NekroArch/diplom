package com.example.addonservice.service.Impl;

import com.example.addonservice.dao.AddonsRepository;
import com.example.addonservice.dto.AddonsDto;
import com.example.addonservice.mapper.AddonsMapper;
import com.example.addonservice.service.AddonService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddonServiceImpl implements AddonService {

    @Autowired
    private AddonsRepository addonsRepository;

    @Autowired
    private AddonsMapper addonsMapper;

    @RabbitListener(queues = "${rabbitmq.queue.getall.addon}")
    @Override
    public List<AddonsDto> getAll(Pageable pageable) {
        return addonsRepository.findAll(pageable).map(addonsMapper::mapToAddonsDto).toList();
    }

    @RabbitListener(queues = "${rabbitmq.queue.get.addon}")
    @Override
    public AddonsDto getById(Integer id) {
        return addonsMapper.mapToAddonsDto(addonsRepository.findById(id).get());
    }

    @RabbitListener(queues = "${rabbitmq.queue.save.addon}")
    @Override
    @Transactional
    public void save(AddonsDto addonDto) {
        addonsRepository.save(addonsMapper.mapToAddons(addonDto));
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.addon}")
    @Override
    @Transactional
    public void delete(Integer id) {
        addonsRepository.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.queue.update.addon}")
    @Override
    @Transactional
    public void update(AddonsDto addonDto) {
        addonsRepository.save(addonsMapper.mapToAddons(addonDto));
    }
}
