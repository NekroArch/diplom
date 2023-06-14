package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordered_dishes")
public class OrderedDishes {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_item_id")
    private DishItems dishItems;
    @JoinColumn(name = "quantity")
    private Integer quantity;

}
