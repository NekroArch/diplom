package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DishesDto {
    Integer id;

    private String name;

    private BigDecimal price;

    private List<AddonsDto> dishesAddons;

    private List<DishesIngredientsDto> dishesIngredients;

}
