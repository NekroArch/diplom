package org.example.service.Impl.sendtype;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.dto.DishForCartDto;

@Getter
@Setter
@Data
@SuperBuilder
public class SendDishItemForCart {
    private DishForCartDto dishForCartDto;
    private Integer id;
}
