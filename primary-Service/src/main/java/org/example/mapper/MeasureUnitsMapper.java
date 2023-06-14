package org.example.mapper;

import org.example.dto.MeasureUnitsDto;
import org.example.entity.MeasureUnits;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasureUnitsMapper {
    MeasureUnitsDto mapToMeasureUnitsDto(MeasureUnits measureUnits);

    MeasureUnits mapToMeasureUnits(MeasureUnitsDto measureUnitsDto);
}
