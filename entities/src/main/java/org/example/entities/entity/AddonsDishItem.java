package org.example.entities.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addons_dish_items")
public class AddonsDishItem{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addons_id")
    private Addons addons;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_items_id")
    private DishItems dishItems;

    @Column(name = "quantity")
    private Integer quantity;
}
