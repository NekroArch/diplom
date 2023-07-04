package org.example.demo.dto;

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
public class IngredientsDto{
    private String name;
    private BigDecimal quantity;
    private MeasureUnitsDto measureUnits;
}
