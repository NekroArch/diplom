package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes_addons")
@IdClass(DishesAddons.PrimaryKey.class)
public class DishesAddons {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addon_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Addons addons;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Dishes dish;
    @JoinColumn(name = "quantity")
    private Integer quantity;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryKey implements Serializable {
        private Addons addons;
        private Dishes dish;
    }
}
