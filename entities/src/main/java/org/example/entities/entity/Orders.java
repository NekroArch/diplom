package org.example.entities.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "orders")
public class Orders extends AbstractEntity {

    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne

    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    private Users users;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    @ToString.Exclude
    @HashCodeExclude
    @EqualsExclude
    List<OrderedDishes> orderedDishesList;
}
