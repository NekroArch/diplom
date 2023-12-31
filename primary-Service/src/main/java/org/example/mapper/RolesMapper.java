package org.example.mapper;

import org.example.dto.AddonsCategoryDto;
import org.example.dto.RolesDto;
import org.example.entity.AddonsCategory;
import org.example.entity.Roles;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RolesMapper {
    RolesDto mapToRoleDto(Roles roles);

    Roles mapToRole(RolesDto rolesDto);
}
