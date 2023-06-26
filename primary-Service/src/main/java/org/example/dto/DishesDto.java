package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DishesDto extends AbstractDto {
    private String name;
    private BigDecimal price;
    private List<AddonsDto> dishesAddons;
    private List<DishesIngredientsDto> dishesIngredients;
}
