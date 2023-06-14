package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "measure_units")
public class MeasureUnits extends AbstractEntity {
    @Column(name = "si_unit")
    private String siUnit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "measureUnits")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private List<Ingredients> ingredients;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "measureUnits")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private List<DishesIngredients> dishesIngredients;
}
