package org.example.dao.Impl;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.example.dao.CartsDao;
import org.example.entities.entity.Carts;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.Users;
import org.springframework.stereotype.Repository;

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
