package com.example.addonservice.mapper;

import com.example.addonservice.dto.AddonsDto;
import org.example.entities.entity.Addons;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddonsMapper {
    AddonsDto mapToAddonsDto(Addons addons);

    Addons mapToAddons(AddonsDto addonsDto);
}
