package com.example.statusservice.mapper;

import com.example.statusservice.dto.StatusDto;
import org.example.entities.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusDto mapToStatusDto(Status status);

    Status mapToStatus(StatusDto statusDto);
}
