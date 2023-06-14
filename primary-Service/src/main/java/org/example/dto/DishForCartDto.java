package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.entity.Addons;
import org.example.entity.Dishes;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishForCartDto {
    private Integer dishId;
    private Map<Integer, Integer> addonIdToQuantity;
    private Integer cartId;
    private Integer dishQuantity;
}
