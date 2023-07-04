package org.example.cartservice.mapper;

import org.example.cartservice.dto.CartDishesDto;
import org.example.cartservice.dto.CartsDto;
import org.example.entities.entity.CartDishes;
import org.example.entities.entity.Carts;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CartsMapper {
    @Autowired
    protected DishItemMapper dishItemMapper;

    public abstract CartsDto mapToCartsDto(Carts carts);

    public abstract Carts mapToCarts(CartsDto cartsDto);
    protected List<CartDishesDto> mapListCartDishesToListCartDishesDto(List<CartDishes> cartDishes) {
        List<CartDishesDto> cartDishesDto = cartDishes.stream().map(x -> {
            CartDishesDto build = CartDishesDto.builder()
                                               .quantity(x.getQuantity())
                                               .dishItems(dishItemMapper.mapToDishItemDto(x.getDishItems())).build();
            return build;
        }).toList();

        return cartDishesDto;
    }
}
