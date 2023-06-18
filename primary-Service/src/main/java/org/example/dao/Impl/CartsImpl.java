package org.example.dao.Impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.dao.CartsDao;
import org.example.entity.Carts;
import org.example.entity.Dishes;
import org.example.entity.Users;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartsImpl extends AbstractDaoImpl<Carts> implements CartsDao {

    @Override
    protected Class<Carts> getEntityClass() {
        return Carts.class;
    }


    @Override
    public Carts getDishInCartsWithFetchById(Integer id) {
        return null;
    }

    @Override
    public void clearCardByUserId(Integer userId) {
        Query nativeQuery = entityManager.createNativeQuery("""
                delete
                from cart_dishes
                where cart_id = (select cd.cart_id
                                 FROM cart_dishes as cd
                                          join carts c on c.id = cd.cart_id
                                          join users u on u.id = c.user_id
                                 where u.id = :userId);
                """);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.executeUpdate();
    }

    @Override
    public Integer getCartIdByUserId(Integer id) {
        return entityManager.createQuery("""
                select c.id from Carts c
                where c.user.id = :id
                """, Users.class).setParameter("id", id).getSingleResult().getId();
    }


}
