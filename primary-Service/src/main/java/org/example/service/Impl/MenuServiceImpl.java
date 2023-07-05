package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MenuDto;
import org.example.dto.StatusDto;
import org.example.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    @Value("${routing.key.queue.menu.delete}")
    private String menuDeleteRoutingKey;
    @Value("${routing.key.queue.menu.update}")
    private String menuUpdateRoutingKey;
    @Value("${routing.key.queue.menu.save}")
    private String menuSaveRoutingKey;
    @Value("${routing.key.queue.menu.getbyname}")
    private String menuGetByNameRoutingKey;

    @Value("${menu.exchange}")
    private String menuExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;
    
    @Override
    public List<MenuDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "MENU" + "/menu?page={page}&size={size}",
                MenuDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
    }


    @Override
    public MenuDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return restTemplate.getForObject("http://"+ "MENU" + "/menu/{id}", MenuDto.class, id );
    }

    @Override
    public MenuDto save(MenuDto entityDto){
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                menuExchange,
                menuSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<MenuDto>() {
                });
    }
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(menuExchange, menuDeleteRoutingKey, id);
    }
    @Transactional
    @Override
    public MenuDto update(MenuDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                menuExchange,
                menuUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<MenuDto>() {
                });
    }

    @Override
    public MenuDto getDishInMenuByName(String name) {
        log.debug("Executing method getDishInMenuByName with Menu name {}", name);

        return rabbitTemplate.convertSendAndReceiveAsType(
                menuExchange,
                menuGetByNameRoutingKey,
                name,
                new ParameterizedTypeReference<MenuDto>() {
                });
    }
}
