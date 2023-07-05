package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.*;
import org.example.dto.DishForCartDto;
import org.example.dto.DishItemDto;
import org.example.service.DishItemService;
import org.example.service.Impl.SendType.SendDishItemForCart;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishItemImpl implements DishItemService {

    @Value("${routing.key.queue.dishitem.delete}")
    private String dishItemDeleteRoutingKey;
    @Value("${routing.key.queue.dishitem.update}")
    private String dishItemUpdateRoutingKey;
    @Value("${routing.key.queue.dishitem.save}")
    private String dishItemSaveRoutingKey;
    @Value("${routing.key.queue.dishitem.saveforcart}")
    private String dishItemSaveForCartRoutingKey;

    @Value("${dishitem.exchange}")
    private String dishItemExchange;

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public List<DishItemDto> getAll(Pageable pageable) {
        log.debug("Executing method getAll");
//        try {
//            return dishItemDao.getAll(pageable).stream().map(dishItemMapper::mapToDishItemDto).toList();
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return List.of(Objects.requireNonNull(restTemplate.getForObject("http://" + "DISHITEM" + "/dish-items?page={page}&size={size}",
                DishItemDto[].class,
                pageable.getPageNumber(), pageable.getPageSize())));
    }

    @Override
    public DishItemDto getById(int id) {
        log.debug("Executing method getById with id {}", id);
//        try {
//            return dishItemMapper.mapToDishItemDto(dishItemDao.getById(id));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return restTemplate.getForObject("http://" + "DISHITEM" + "/dish-items/{id}", DishItemDto.class, id);
    }

    @Transactional
    @Override
    public DishItemDto save(DishItemDto entityDto) {
        log.debug("Executing method save with {}", entityDto);
//        try {
//            return dishItemMapper.mapToDishItemDto(dishItemDao.save(dishItemMapper.mapToDishItems(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        return rabbitTemplate.convertSendAndReceiveAsType(
                dishItemExchange,
                dishItemSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<DishItemDto>() {
                });

    }

    @Transactional
    @Override
    public void delete(int id) {
        log.debug("Executing method delete with id {}", id);
//        try {
//            dishItemDao.delete(id);
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        rabbitTemplate.convertAndSend(dishItemExchange, dishItemDeleteRoutingKey, id);
    }

    @Transactional
    @Override
    public DishItemDto update(DishItemDto entityDto) {
        log.debug("Executing method update with {}", entityDto);
//        try {
//            return dishItemMapper.mapToDishItemDto(dishItemDao.update(dishItemMapper.mapToDishItems(entityDto)));
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }


        return rabbitTemplate.convertSendAndReceiveAsType(
                dishItemExchange,
                dishItemUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<DishItemDto>() {
                });

    }

    @Transactional
    @Override
    public void deleteDishFromCart(Integer dishId) {
//        try {
//            dishItemDao.delete(dishId);
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }

        rabbitTemplate.convertAndSend(dishItemExchange, dishItemDeleteRoutingKey, dishId);
    }

    @Transactional
    @Override
    public void saveDishItemInCartWithRelations(DishForCartDto dishForCartDto, Integer id) {
        log.debug("Executing method saveDishItemInCartWithRelations with {}", dishForCartDto);

//        BigDecimal price = calculatePriceForTheDish(dishForCartDto);
//
//        DishItems dishItems = saveDishItem(dishForCartDto, price);
//
//        saveRelationsBetweenDishItemAndAddons(dishForCartDto, dishItems);
//
//        saveRelationsBetweenDishItemAndCarts(dishForCartDto, dishItems, id);

        SendDishItemForCart sendDishItemForCart = SendDishItemForCart.builder().dishForCartDto(dishForCartDto).id(id).build();

        rabbitTemplate.convertAndSend(dishItemExchange, dishItemSaveForCartRoutingKey, sendDishItemForCart);

    }

//    private void saveRelationsBetweenDishItemAndCarts(DishForCartDto dishForCartDto, DishItems dishItems, Integer id) {
//        cartDishDao.save(CartDishes.builder()
//                                   .dishItems(DishItems.builder().id(dishItems.getId()).build())
//                                   .cart(Carts.builder().id(cartsDao.getCartIdByUserId(id)).build())
//                                   .quantity(dishForCartDto.getDishQuantity())
//                                   .build());
//    }
//
//    private void saveRelationsBetweenDishItemAndAddons(DishForCartDto dishForCartDto, DishItems dishItems) {
//        dishForCartDto.getAddonIdToQuantity().forEach((key, value) -> {
//            addonsDishItemDao.save(AddonsDishItem.builder()
//                                                 .addons(Addons.builder().id(key).build())
//                                                 .dishItems(DishItems.builder().id(dishItems.getId()).build())
//                                                 .quantity(value)
//                                                 .build());
//        });
//    }
//
//    private DishItems saveDishItem(DishForCartDto dishForCartDto, BigDecimal price) {
//        try {
//            return dishItemDao.save(DishItems.builder()
//                                             .dishes(Dishes.builder().id(dishForCartDto.getDishId()).build())
//                                             .price(price)
//                                             .build());
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private BigDecimal calculatePriceForTheDish(DishForCartDto dishForCartDto) {
//        BigDecimal price = dishPrice(dishForCartDto.getDishId());
//
//        price = price.add(dishForCartDto.getAddonIdToQuantity()
//                                        .entrySet()
//                                        .stream()
//                                        .map(x -> addonPrice(x.getKey()).multiply(BigDecimal.valueOf(x.getValue())))
//                                        .reduce(BigDecimal::add)
//                                        .orElse(BigDecimal.ZERO));
//        return price;
//    }
//
//    private BigDecimal addonPrice(Integer addonId) {
//        try {
//            return addonsDao.getById(addonId).getPrice();
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private BigDecimal dishPrice(Integer dishId) {
//        try {
//            return dishesDao.getById(dishId).getPrice();
//        } catch (InterruptedException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
