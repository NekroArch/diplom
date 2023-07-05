package com.example.measureunitservice.mapper;

import com.example.measureunitservice.dto.MeasureUnitsDto;
import org.example.entities.entity.MeasureUnits;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasureUnitsMapper {
    MeasureUnitsDto mapToMeasureUnitsDto(MeasureUnits measureUnits);

    MeasureUnits mapToMeasureUnits(MeasureUnitsDto measureUnitsDto);
}
