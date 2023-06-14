package org.example.mapper;

import org.example.dto.CartDishesDto;
import org.example.dto.OrderedDishesDto;
import org.example.dto.OrdersDto;
import org.example.entity.CartDishes;
import org.example.entity.OrderedDishes;
import org.example.entity.Orders;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrdersMapper {

    @Autowired
    DishItemMapper dishItemMapper;

    public abstract Orders mapToOrders(OrdersDto ordersDTO);

    public abstract OrdersDto mapToOrdersDTO(Orders orders);

    protected List<OrderedDishesDto> mapListOrderedDishesDtoToListOrderedDishesDto(List<OrderedDishes> orderedDishes) {
        List<OrderedDishesDto> orderedDishesDto = orderedDishes.stream().map(x -> {
            OrderedDishesDto build = OrderedDishesDto.builder()
                                               .quantity(x.getQuantity())
                                               .dishItems(dishItemMapper.mapToDishItemDto(x.getDishItems())).build();
            return build;
        }).toList();

        return orderedDishesDto;
    }
}
