package org.example.dishitemservice.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.entities.entity.CartDishes;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartDishDaoImpl implements CartDishDao{

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void save(CartDishes cartDishes) {
        entityManager.persist(cartDishes);
    }
}
