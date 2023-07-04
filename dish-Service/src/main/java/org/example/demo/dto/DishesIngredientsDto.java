package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
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
}