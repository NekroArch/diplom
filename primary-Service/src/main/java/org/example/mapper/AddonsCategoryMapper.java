package org.example.mapper;

import org.example.dto.AddonsCategoryDto;
import org.example.entity.AddonsCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddonsCategoryMapper {
    AddonsCategoryDto mapToAddonsCategoryDto(AddonsCategory addonsCategory);

    AddonsCategory mapToAddonsCategory(AddonsCategoryDto addonsCategoryDto);
}
