package org.example.mapper;

import org.example.dto.DishItemDto;
import org.example.entities.entity.DishItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DishItemMapper {
    @Autowired
    protected AddonsDishItemMapper addonsDishItemMapper;

    @Mapping(target = "addonsDishItemList", expression = "java(dishItems.getAddonsDishItemList().stream().map(addonsDishItemMapper::mapAddonsDishItemToAddonsDishItemDto).toList())")
    @Mapping(target = "dishesName", expression = "java(dishItems.getDishes().getName())")
    public abstract DishItemDto mapToDishItemDto(DishItems dishItems);

    public abstract DishItems mapToDishItems(DishItemDto dishItemDto);
}
