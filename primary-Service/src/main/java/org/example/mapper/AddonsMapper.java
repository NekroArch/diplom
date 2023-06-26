package org.example.mapper;

import org.example.dto.AddonsDto;
import org.example.entities.entity.Addons;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddonsMapper {
    AddonsDto mapToAddonsDto(Addons addons);

    Addons mapToAddons(AddonsDto addonsDTO);
}
