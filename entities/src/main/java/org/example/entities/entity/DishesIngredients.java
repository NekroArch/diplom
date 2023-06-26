package org.example.entities.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes_ingredients")
public class DishesIngredients {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Ingredients ingredient;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Dishes dish;
    @Column(name = "volume")
    private BigDecimal volume;

}
