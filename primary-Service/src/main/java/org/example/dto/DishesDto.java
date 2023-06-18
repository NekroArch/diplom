package org.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.entity.Addons;
import org.example.entity.Ingredients;
import org.example.entity.Menu;
import org.example.entity.Orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
