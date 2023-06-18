package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "dishes")
public class Dishes extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "base_price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Menu menu;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dishes_addons",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "addon_id")})
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private List<Addons> dishesAddons;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private List<DishesIngredients> dishesIngredients;
}
