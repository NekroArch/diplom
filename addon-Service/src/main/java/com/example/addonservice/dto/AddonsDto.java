package com.example.addonservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddonsDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private AddonsCategoryDto category;
}
