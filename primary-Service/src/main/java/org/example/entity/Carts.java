package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "carts")
public class Carts extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private List<CartDishes> cardDishes;
}
