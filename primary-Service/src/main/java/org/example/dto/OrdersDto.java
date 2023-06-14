package org.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrdersDto extends AbstractDto {
    private BigDecimal price;
    private StatusDto status;
    private List<OrderedDishesDto> orderedDishesList;
}
