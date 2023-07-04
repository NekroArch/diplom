package org.example.roleservice.service.Impl;

import org.example.roleservice.dao.RoleRepository;
import org.example.roleservice.dto.RolesDto;
import org.example.roleservice.mapper.RoleMapper;
import org.example.roleservice.service.RoleService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository addonsRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @RabbitListener(queues = "rabbitmq.queue.getall.role")
    public List<RolesDto> getAll(Pageable pageable) {
        return addonsRepository.findAll(pageable).map(roleMapper::mapToRolesDto).toList();
    }

    @RabbitListener(queues = "rabbitmq.queue.get.role")
    @Override
    public RolesDto getById(Integer id) {
        return roleMapper.mapToRolesDto(addonsRepository.findById(id).get());
    }

    @RabbitListener(queues = "rabbitmq.queue.save.role")
    @Override
    @Transactional
    public void save(RolesDto addonDto) {
        addonsRepository.save(roleMapper.mapToRoles(addonDto));
    }

    @RabbitListener(queues = "rabbitmq.queue.delete.role")
    @Override
    @Transactional
    public void delete(Integer id) {
        addonsRepository.deleteById(id);
    }

    @RabbitListener(queues = "rabbitmq.queue.update.role")
    @Override
    @Transactional
    public void update(RolesDto addonDto) {
        addonsRepository.save(roleMapper.mapToRoles(addonDto));
    }
}
