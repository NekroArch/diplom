package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MeasureUnitsDto;
import org.example.service.MeasureUnitsService;
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
public class MeasureUnitsServiceImpl implements MeasureUnitsService {

    @Value("${routing.key.queue.measureunit.delete}")
    private String measureUnitsDeleteRoutingKey;
    @Value("${routing.key.queue.measureunit.update}")
    private String measureUnitsUpdateRoutingKey;
    @Value("${routing.key.queue.measureunit.save}")
    private String measureUnitsSaveRoutingKey;

    @Value("${measureunit.exchange}")
    private String measureUnitsExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<MeasureUnitsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "http://" + "MEASUREUNIT" + "/measure-units?page={page}&size={size}",
                MeasureUnitsDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));    }

    @Override
    public MeasureUnitsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return restTemplate.getForObject("http://"+ "MEASUREUNIT" + "/measure-units/{id}", MeasureUnitsDto.class, id );
    }

    @Transactional
    @Override
    public MeasureUnitsDto save(MeasureUnitsDto entityDto){
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                measureUnitsExchange,
                measureUnitsSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<MeasureUnitsDto>() {
                });
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(measureUnitsExchange, measureUnitsDeleteRoutingKey, id);
    }

    @Transactional
    @Override
    public MeasureUnitsDto update(MeasureUnitsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                measureUnitsExchange,
                measureUnitsUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<MeasureUnitsDto>() {
                });    }
}
