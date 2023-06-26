package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishForCartDto {
    private Integer dishId;
    private Map<Integer, Integer> addonIdToQuantity;
    private Integer dishQuantity;
}
