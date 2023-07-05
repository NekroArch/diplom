package com.example.addoncategoryservice.mapper;

import com.example.addoncategoryservice.dto.AddonCategoryDto;
import org.example.entities.entity.AddonsCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddonCategoryMapper {
    AddonCategoryDto mapToAddonsCategoryDto(AddonsCategory addonsCategory);

    AddonsCategory mapToAddonsCategory(AddonCategoryDto addonCategoryDto);
}
