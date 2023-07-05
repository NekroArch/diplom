package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddonsCategoryDto;
import org.example.service.AddonsCategoryService;
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
public class AddonsCategoryServiceImpl implements AddonsCategoryService {

    @Value("${routing.key.queue.addoncategory.delete}")
    private String addonCategoryDeleteRoutingKey;
    @Value("${routing.key.queue.addoncategory.update}")
    private String addonCategoryUpdateRoutingKey;
    @Value("${routing.key.queue.addoncategory.save}")
    private String addonCategorySaveRoutingKey;

    @Value("${addoncategory.exchange}")
    private String addonCategoryExchange;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public List<AddonsCategoryDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return List.of(Objects.requireNonNull(restTemplate.getForObject(
                "http://" + "ADDONCATEGORY" + "/addon-category?page={page}&size={size}",
                AddonsCategoryDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));    }

    @Override
    public AddonsCategoryDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return restTemplate.getForObject("http://" + "ADDONCATEGORY" + "/addon-category/{id}",
                AddonsCategoryDto.class,
                id);    }

    @Transactional
    @Override
    public AddonsCategoryDto save(AddonsCategoryDto entityDto) {
        log.debug("Executing method save with {}", entityDto);

        return rabbitTemplate.convertSendAndReceiveAsType(
                addonCategoryExchange,
                addonCategorySaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<AddonsCategoryDto>() {
                });
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        rabbitTemplate.convertAndSend(addonCategoryExchange, addonCategoryDeleteRoutingKey, id);
    }

    @Transactional
    @Override
    public AddonsCategoryDto update(AddonsCategoryDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return rabbitTemplate.convertSendAndReceiveAsType(
                addonCategoryExchange,
                addonCategoryUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<AddonsCategoryDto>() {
                });
    }
}
