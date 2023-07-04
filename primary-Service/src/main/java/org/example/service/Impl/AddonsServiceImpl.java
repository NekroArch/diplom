package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.AddonsDao;
import org.example.dto.AddonsDto;
import org.example.dto.DishesDto;
import org.example.mapper.AddonsMapper;
import org.example.service.AddonsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddonsServiceImpl implements AddonsService {
//    private final AddonsDao addonsDao;
//    private final AddonsMapper addonsMapper;

    @Value("${routing.key.queue.addon.delete}")
    private String addonDeleteRoutingKey;
    @Value("${routing.key.queue.addon.update}")
    private String addonUpdateRoutingKey;
    @Value("${routing.key.queue.addon.save}")
    private String addonSaveRoutingKey;

    @Value("${addon.exchange}")
    private String addonExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<AddonsDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "http://" + "ADDON" + "/addons?page={page}&size={size}",
                AddonsDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
       // return addonsDao.getAll(pageable).stream().map(addonsMapper::mapToAddonsDto).toList();
    }

    @Override
    public AddonsDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return restTemplate.getForObject("http://" + "ADDON" + "/addons/{id}",
                AddonsDto.class,
                id);
       // return addonsMapper.mapToAddonsDto(addonsDao.getById(id));
    }

    @Transactional
    @Override
    public AddonsDto save(AddonsDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                addonExchange,
                addonSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<AddonsDto>() {
                });
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(addonExchange, addonDeleteRoutingKey);
    }

    @Transactional
    @Override
    public AddonsDto update(AddonsDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                addonExchange,
                addonUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<AddonsDto>() {
                });
    }
}
