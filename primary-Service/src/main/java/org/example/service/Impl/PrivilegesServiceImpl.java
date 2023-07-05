package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PrivilegesDto;
import org.example.dto.RolesDto;
import org.example.service.PrivilegesService;
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
public class PrivilegesServiceImpl implements PrivilegesService {


    @Value("${routing.key.queue.privileges.delete}")
    private String privilegesDeleteRoutingKey;
    @Value("${routing.key.queue.privileges.update}")
    private String privilegesUpdateRoutingKey;
    @Value("${routing.key.queue.privileges.save}")
    private String privilegesSaveRoutingKey;

    @Value("${privileges.exchange}")
    private String privilegesExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;
    
    @Override
    public List<PrivilegesDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "PRIVILEGES" + "/privileges?page={page}&size={size}",
                PrivilegesDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));

        //return privilegesDao.getAll(pageable).stream().map(privilegesMapper::mapToPrivilegesDto).toList();
    }

    @Override
    public PrivilegesDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);

        return restTemplate.getForObject("http://"+ "PRIVILEGES" + "/privileges/{id}", PrivilegesDto.class, id );

        //return privilegesMapper.mapToPrivilegesDto(privilegesDao.getById(id));
    }

    @Transactional
    @Override
    public PrivilegesDto save(PrivilegesDto entityDto){
        log.debug("Executing method save with {}", entityDto);
//        try {
//            return privilegesMapper.mapToPrivilegesDto(privilegesDao.save(privilegesMapper.mapToPrivileges(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return rabbitTemplate.convertSendAndReceiveAsType(
                privilegesExchange,
                privilegesSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<PrivilegesDto>() {
                });

    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(privilegesExchange, privilegesDeleteRoutingKey, id);

    }

    @Transactional
    @Override
    public PrivilegesDto update(PrivilegesDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                privilegesExchange,
                privilegesUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<PrivilegesDto>() {
                });
    }
}
