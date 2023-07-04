package org.example.dishitemservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendDishForCart {
    DishForCartDto dishForCartDto;
    Integer id;
}
