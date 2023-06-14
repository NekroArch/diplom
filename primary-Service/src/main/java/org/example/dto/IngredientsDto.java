package org.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IngredientsDto extends AbstractDto{
    private String name;
    private BigDecimal quantity;
    private MeasureUnitsDto measureUnitsId;
}
