package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.StatusDto;
import org.example.service.StatusService;
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
public class StatusServiceImpl implements StatusService {

    @Value("${routing.key.queue.status.delete}")
    private String statusDeleteRoutingKey;
    @Value("${routing.key.queue.status.update}")
    private String statusUpdateRoutingKey;
    @Value("${routing.key.queue.status.save}")
    private String statusSaveRoutingKey;

    @Value("${status.exchange}")
    private String statusExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;
    
    @Override
    public List<StatusDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "STATUS" + "/status?page={page}&size={size}",
                StatusDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
    }

    @Override
    public StatusDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return restTemplate.getForObject("http://"+ "STATUS" + "/status/{id}", StatusDto.class, id );
    }


    @Override
    public StatusDto save(StatusDto entityDto){
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                statusExchange,
                statusSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<StatusDto>() {
                });
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(statusExchange, statusDeleteRoutingKey, id);
    }
    @Transactional
    @Override
    public StatusDto update(StatusDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                statusExchange,
                statusUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<StatusDto>() {
                });
    }
}
