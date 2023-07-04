package org.example.roleservice.mapper;

import org.example.entities.entity.Roles;
import org.example.roleservice.dto.RolesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RolesDto mapToRolesDto(Roles roles);

    Roles mapToRoles(RolesDto rolesDto);
}
