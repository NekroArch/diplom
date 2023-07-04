package org.example.dishitemservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dishitemservice.dao.AddonsDishItemDao;
import org.example.dishitemservice.dao.CartDishDao;
import org.example.dishitemservice.dao.DishItemRepository;
import org.example.dishitemservice.dto.CartsDto;
import org.example.dishitemservice.dto.DishForCartDto;
import org.example.dishitemservice.dto.DishItemDto;
import org.example.dishitemservice.dto.SendDishForCart;
import org.example.dishitemservice.mapper.DishItemMapper;
import org.example.dishitemservice.service.DishItemService;
import org.example.entities.entity.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishItemImpl implements DishItemService {
    private final DishItemRepository dishItemDao;
    private final DishItemMapper dishItemMapper;
    private final AddonsDishItemDao addonsDishItemDao;
    private final CartDishDao cartDishDao;

    private final RabbitTemplate rabbitTemplate;

    private String cartExchange = "cart-exchange";
    private String addonExchange = "addon-exchange";
    private String dishExchange = "dish-exchange";
    private String cartRoutingKey = "queue.cart.get";
    private String addonRoutingKey = "queue.addon.get";
    private String dishRoutingKey = "queue.dish.get";

    @RabbitListener(queues = "${rabbitmq.queue.getall.dishitem}")
    @Override
    public List<DishItemDto> getAll(Pageable pageable) {
        // log.debug("Executing method getAll");
        return dishItemDao.findAll(pageable).stream().map(dishItemMapper::mapToDishItemDto).toList();
    }

    @RabbitListener(queues = "${rabbitmq.queue.get.dishitem}")
    @Override
    public DishItemDto getById(Integer id) {
        return dishItemMapper.mapToDishItemDto(dishItemDao.findById(id).get());
    }

    @RabbitListener(queues = "${rabbitmq.queue.save.dishitem}")
    @Transactional
    @Override
    public DishItemDto save(DishItemDto entityDto) {
        // log.debug("Executing method save with {}", entityDto);
        return dishItemMapper.mapToDishItemDto(dishItemDao.save(dishItemMapper.mapToDishItems(entityDto)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.dishitem}")
    @Override
    @Transactional
    public void delete(Integer id) {
        //log.debug("Executing method delete with id {}", id);
        dishItemDao.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.queue.update.dishitem}")
    @Transactional
    @Override
    public DishItemDto update(DishItemDto entityDto) {
        log.debug("Executing method update with {}", entityDto);
        return dishItemMapper.mapToDishItemDto(dishItemDao.save(dishItemMapper.mapToDishItems(entityDto)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.dishitem}")
    @Transactional
    @Override
    public void deleteDishFromCart(Integer dishId) {
        dishItemDao.deleteById(dishId);
    }

    @RabbitListener(queues = "${rabbitmq.queue.saveforcart.dishitem}")
    @Transactional
    @Override
    public void saveDishItemInCartWithRelations(SendDishForCart dishForCartDto) {
        //log.debug("Executing method saveDishItemInCartWithRelations with {}", dishForCartDto);

        BigDecimal price = calculatePriceForTheDish(dishForCartDto.getDishForCartDto());

        DishItems dishItems = saveDishItem(dishForCartDto.getDishForCartDto(), price);

        saveRelationsBetweenDishItemAndAddons(dishForCartDto.getDishForCartDto(), dishItems);

        saveRelationsBetweenDishItemAndCarts(dishForCartDto.getDishForCartDto(), dishItems, dishForCartDto.getId());

    }

    private void saveRelationsBetweenDishItemAndCarts(DishForCartDto dishForCartDto, DishItems dishItems, Integer id) {
        cartDishDao.save(CartDishes.builder()
                .dishItems(DishItems.builder().id(dishItems.getId()).build())
                .cart(Carts.builder().id(
                        rabbitTemplate.convertSendAndReceiveAsType(
                                cartExchange,
                                cartRoutingKey,
                                id,
                                new ParameterizedTypeReference<CartsDto>() {
                                }).getId()
                ).build())
                .quantity(dishForCartDto.getDishQuantity())
                .build());
    }

    private void saveRelationsBetweenDishItemAndAddons(DishForCartDto dishForCartDto, DishItems dishItems) {
        dishForCartDto.getAddonIdToQuantity().forEach((key, value) -> {
            addonsDishItemDao.save(AddonsDishItem.builder()
                    .addons(Addons.builder().id(key).build())
                    .dishItems(DishItems.builder().id(dishItems.getId()).build())
                    .quantity(value)
                    .build());
        });
    }

    private DishItems saveDishItem(DishForCartDto dishForCartDto, BigDecimal price) {
        return dishItemDao.save(DishItems.builder()
                .dishes(Dishes.builder().id(dishForCartDto.getDishId()).build())
                .price(price)
                .build());
    }

    private BigDecimal calculatePriceForTheDish(DishForCartDto dishForCartDto) {
        BigDecimal price = dishPrice(dishForCartDto.getDishId());

        price = price.add(dishForCartDto.getAddonIdToQuantity()
                .entrySet()
                .stream()
                .map(x -> addonPrice(x.getKey()).multiply(BigDecimal.valueOf(x.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO));
        return price;
    }

    private BigDecimal addonPrice(Integer addonId) {
        return Objects.requireNonNull(rabbitTemplate.convertSendAndReceiveAsType(
                addonExchange,
                addonRoutingKey,
                addonId,
                new ParameterizedTypeReference<Addons>() {
                })).getPrice();
    }

    private BigDecimal dishPrice(Integer dishId) {
        return Objects.requireNonNull(rabbitTemplate.convertSendAndReceiveAsType(
                dishExchange,
                dishRoutingKey,
                dishId,
                new ParameterizedTypeReference<Dishes>() {
                })).getPrice();
    }
}
