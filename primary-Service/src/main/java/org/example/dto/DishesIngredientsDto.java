package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishesIngredientsDto {
    private IngredientsDto ingredient;
    private BigDecimal volume;
    private MeasureUnitsDto measureUnits;
}
