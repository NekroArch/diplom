package org.example.mapper;

import org.example.dto.PrivilegesDto;
import org.example.entities.entity.Privileges;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivilegesMapper {
    PrivilegesDto mapToPrivilegesDto(Privileges privileges);

    Privileges mapToPrivileges(PrivilegesDto privilegesDto);
}
