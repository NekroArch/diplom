package com.example.ingredientsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsDto{
    private Integer id;
    private String name;
    private BigDecimal quantity;
    private MeasureUnitsDto measureUnits;
}
