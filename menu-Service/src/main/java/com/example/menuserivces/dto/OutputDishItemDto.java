package com.example.menuserivces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputDishItemDto {
    private String name;
    private BigDecimal price;
    private List<OutputDishesAddonsDto> dishesAddons;
}
