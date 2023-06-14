package org.example.dao.Impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.dao.CartsDao;
import org.example.entity.Carts;
import org.example.entity.Dishes;
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
    public Carts getById(Integer id) {
        TypedQuery<Carts> query = entityManager.createQuery("""
                SELECT c from Carts as c
                JOIN FETCH CartDishes as cd
                    on cd.cart.id = c.id
                JOIN FETCH DishItems di
                     on di.id = cd.dishItems.id
                JOIN FETCH AddonsDishItem as ad
                     on ad.dishItems.id = di.id
                JOIN FETCH Addons as a
                     on a.id = ad.addons.id
                JOIN FETCH Dishes d
                    on d.id = di.dishes.id
                JOIN FETCH DishesIngredients di2
                    on di2.dish.id = d.id
                JOIN FETCH Ingredients i2
                    on i2.id = di2.ingredient.id
                where c.id = :id
                       """, Carts.class);

        query.setParameter("id", id);
        Carts singleResult = query.getSingleResult();
        return singleResult;
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
}
