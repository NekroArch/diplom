package org.example.entities.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_dishes")
public class CartDishes{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_item_id")
    private DishItems dishItems;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Carts cart;

    @Column(name = "quantity")
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDishes that = (CartDishes) o;
        return Objects.equals(dishItems, that.dishItems) && Objects.equals(cart, that.cart) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishItems, cart, quantity);
    }
}
