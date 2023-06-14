package org.example.mapper;

import org.example.dto.StatusDto;
import org.example.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusDto mapToStatusDto(Status status);

    Status mapToStatus(StatusDto statusDto);
}
