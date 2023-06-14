package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.entity.AddonsDishItem;
import org.example.entity.Dishes;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishItemDto extends AbstractDto {
    private String dishesName;
    private BigDecimal price;
    private List<AddonsDishItemDto> addonsDishItemList;
}
