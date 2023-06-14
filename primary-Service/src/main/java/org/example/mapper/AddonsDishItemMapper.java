package org.example.mapper;

import org.example.dto.AddonsDishItemDto;
import org.example.entity.AddonsDishItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddonsDishItemMapper {
    @Mapping(target = "addonsName", expression = "java(addonsDishItem.getAddons().getName())")
    AddonsDishItemDto mapAddonsDishItemToAddonsDishItemDto(AddonsDishItem addonsDishItem);

    AddonsDishItem mapAddonsDishItemDtoToAddonsDishItem(AddonsDishItemDto addonsDishItemDto);
}
