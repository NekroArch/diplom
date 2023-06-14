package org.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.dto.AbstractDto;
import org.example.dto.AddonsCategoryDto;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddonsDto extends AbstractDto {
    private String name;
    private BigDecimal price;
    private AddonsCategoryDto category;
}
