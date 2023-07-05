package org.example.privilegesservice.mapper;

import org.example.entities.entity.Privileges;
import org.example.privilegesservice.dto.PrivilegesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivilegesMapper {
    PrivilegesDto mapToPrivilegesDto(Privileges privileges);

    Privileges mapToPrivileges(PrivilegesDto privilegesDto);
}
