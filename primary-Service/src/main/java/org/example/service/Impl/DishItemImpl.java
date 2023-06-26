package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.*;
import org.example.dto.DishForCartDto;
import org.example.dto.DishItemDto;
import org.example.entities.entity.*;
import org.example.mapper.DishItemMapper;
import org.example.service.DishItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishItemImpl implements DishItemService {
    private final DishItemDao dishItemDao;
    private final DishItemMapper dishItemMapper;
    private final DishesDao dishesDao;
    private final AddonsDao addonsDao;
    private final AddonsDishItemDao addonsDishItemDao;
    private final CartDishDao cartDishDao;
    private final CartsDao cartsDao;

    @Override
    public List<DishItemDto> getAll(Pageable pageable){
        log.debug("Executing method getAll");
        try {
            return dishItemDao.getAll(pageable).stream().map(dishItemMapper::mapToDishItemDto).toList();
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DishItemDto getById(int id) {
        log.debug("Executing method getById with id {}", id);
        try {
            return dishItemMapper.mapToDishItemDto(dishItemDao.getById(id));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public DishItemDto save(DishItemDto entityDto){
        log.debug("Executing method save with {}", entityDto);
        try {
            return dishItemMapper.mapToDishItemDto(dishItemDao.save(dishItemMapper.mapToDishItems(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        log.debug("Executing method delete with id {}", id);
        try {
            dishItemDao.delete(id);
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public DishItemDto update(DishItemDto entityDto) {
        log.debug("Executing method update with {}", entityDto);
        try {
            return dishItemMapper.mapToDishItemDto(dishItemDao.update(dishItemMapper.mapToDishItems(entityDto)));
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void deleteDishFromCart(Integer dishId) {
        try {
            dishItemDao.delete(dishId);
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void saveDishItemInCartWithRelations(DishForCartDto dishForCartDto, Integer id) {
        log.debug("Executing method saveDishItemInCartWithRelations with {}", dishForCartDto);

        BigDecimal price = calculatePriceForTheDish(dishForCartDto);

        DishItems dishItems = saveDishItem(dishForCartDto, price);

        saveRelationsBetweenDishItemAndAddons(dishForCartDto, dishItems);

        saveRelationsBetweenDishItemAndCarts(dishForCartDto, dishItems, id);

    }

    private void saveRelationsBetweenDishItemAndCarts(DishForCartDto dishForCartDto, DishItems dishItems, Integer id) {
        cartDishDao.save(CartDishes.builder()
                                   .dishItems(DishItems.builder().id(dishItems.getId()).build())
                                   .cart(Carts.builder().id(cartsDao.getCartIdByUserId(id)).build())
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
        try {
            return dishItemDao.save(DishItems.builder()
                                             .dishes(Dishes.builder().id(dishForCartDto.getDishId()).build())
                                             .price(price)
                                             .build());
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
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
        try {
            return addonsDao.getById(addonId).getPrice();
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal dishPrice(Integer dishId) {
        try {
            return dishesDao.getById(dishId).getPrice();
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
