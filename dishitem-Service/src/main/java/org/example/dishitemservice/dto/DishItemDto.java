package org.example.dishitemservice.dto;

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
public class DishItemDto{
    private Integer id;
    private String dishesName;
    private BigDecimal price;
    private List<AddonsDishItemDto> addonsDishItemList;
}
