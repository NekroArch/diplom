package org.example.privilegesservice.service.Impl;

import org.example.privilegesservice.dao.PrivilegesRepository;
import org.example.privilegesservice.dto.PrivilegesDto;
import org.example.privilegesservice.mapper.PrivilegesMapper;
import org.example.privilegesservice.service.PrivilegesService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PrivilegesServiceImpl implements PrivilegesService {

    private final PrivilegesMapper privilegesMapper;

    private final PrivilegesRepository privilegesRepository;

    @Autowired
    public PrivilegesServiceImpl(PrivilegesMapper privilegesMapper, PrivilegesRepository privilegesRepository) {
        this.privilegesMapper = privilegesMapper;
        this.privilegesRepository = privilegesRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.privileges}")
    public List<PrivilegesDto> getAll(Pageable pageable){
        return privilegesRepository.findAll(pageable).map(privilegesMapper::mapToPrivilegesDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.privileges}")
    public PrivilegesDto getById(Integer id){
        return privilegesRepository.findById(id).map(privilegesMapper::mapToPrivilegesDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.privileges}")
    @Transactional
    public void save(PrivilegesDto privilegessDto){
        privilegesRepository.save(privilegesMapper.mapToPrivileges(privilegessDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.privileges}")
    @Transactional
    public void delete(Integer id){
        privilegesRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.privileges}")
    @Transactional
    public void update(PrivilegesDto privilegessDto){
        privilegesRepository.save(privilegesMapper.mapToPrivileges(privilegessDto));
    }
}
