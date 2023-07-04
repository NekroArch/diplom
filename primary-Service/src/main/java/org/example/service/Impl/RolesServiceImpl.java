package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.RolesDao;
import org.example.dao.UserDao;
import org.example.dto.DishesDto;
import org.example.dto.RolesDto;
import org.example.dto.UsersDto;
import org.example.mapper.RolesMapper;
import org.example.service.RolesService;
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
public class RolesServiceImpl implements RolesService {

//    private final RolesDao rolesDao;
//
//    private final RolesMapper rolesMapper;

    @Value("${routing.key.queue.role.delete}")
    private String roleDeleteRoutingKey;
    @Value("${routing.key.queue.role.update}")
    private String roleUpdateRoutingKey;
    @Value("${routing.key.queue.role.save}")
    private String roleSaveRoutingKey;

    @Value("${role.exchange}")
    private String roleExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<RolesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "ROLE" + "/roles?page={page}&size={size}",
                RolesDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));

        //return rolesDao.getAll(pageable).stream().map(rolesMapper::mapToRoleDto).toList();
    }

    @Override
    public RolesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);

        return restTemplate.getForObject("http://"+ "ROLE" + "/roles/{id}", RolesDto.class, id );

        //return rolesMapper.mapToRoleDto(rolesDao.getById(id));
    }

    @Transactional
    @Override
    public RolesDto save(RolesDto entityDto){
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                roleExchange,
                roleSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<RolesDto>() {
                });

//        try {
//            return rolesMapper.mapToRoleDto(rolesDao.save(rolesMapper.mapToRole(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);

        rabbitTemplate.convertAndSend(roleExchange, roleDeleteRoutingKey, id);

//        rolesDao.delete(id);
    }

    @Transactional
    @Override
    public RolesDto update(RolesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                roleExchange,
                roleUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<RolesDto>() {
                });

//        return rolesMapper.mapToRoleDto(rolesDao.update(rolesMapper.mapToRole(entityDto)));
    }
}
