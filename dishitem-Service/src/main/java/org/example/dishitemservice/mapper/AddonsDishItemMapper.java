package org.example.dishitemservice.mapper;

import org.example.dishitemservice.dto.AddonsDishItemDto;
import org.example.entities.entity.AddonsDishItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddonsDishItemMapper {
    @Mapping(target = "addonsName", expression = "java(addonsDishItem.getAddons().getName())")
    AddonsDishItemDto mapAddonsDishItemToAddonsDishItemDto(AddonsDishItem addonsDishItem);

    AddonsDishItem mapAddonsDishItemDtoToAddonsDishItem(AddonsDishItemDto addonsDishItemDto);
}
