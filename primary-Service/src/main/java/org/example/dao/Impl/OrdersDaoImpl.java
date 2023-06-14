package org.example.dao.Impl;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dao.OrdersDao;
import org.example.dto.Pageable;
import org.example.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersDaoImpl extends AbstractDaoImpl<Orders> implements OrdersDao {


    @Override
    protected Class<Orders> getEntityClass() {
        return Orders.class;
    }

    @Override
    public Orders getOrdersWithFetchById(Integer id) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
        Root<Orders> o = cq.from(Orders.class);
        o.fetch("orderedDishesList", JoinType.INNER);
        cq.select(o).where(cb.equal(o.get(Orders_.ID), id));
        return entityManager.createQuery(cq).getSingleResult();

    }

    @Override
    public Integer saveOrderFromCart(Integer id) {

        Query nativeQuery = entityManager.createNativeQuery("""
                                insert into orders (user_id, price)
                                select :id, out.price
                                from (select sum(d.price * cd.quantity) as price
                                      from dish_items d
                                      join cart_dishes cd on cd.dish_item_id = d.id
                                      join carts c on cd.cart_id = c.id
                                      where c.user_id = :id) out
                                returning orders.id
                """);

        nativeQuery.setParameter("id", id);
        return (Integer) nativeQuery.getSingleResult();
    }

    @Override
    public void saveOrderedDishesRelations(Integer userId, Integer orderId) {
        Query query = entityManager.createNativeQuery("""
                insert into ordered_dishes (dish_item_id, order_id, quantity)
                SELECT cd.dish_item_id, :id, quantity
                FROM cart_dishes cd
                         join carts c on c.id = cd.cart_id
                         join users u on u.id = c.user_id
                where u.id = :userId
                """);
        query.setParameter("id", orderId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public List<Orders> getAllOrdersForUserById(Integer id, Pageable pageable) {
        TypedQuery<Orders> query = entityManager.createQuery("""
                select o from Orders o
                join fetch OrderedDishes od on od.orders.id = o.id
                join fetch DishItems di on di.id = od.dishItems.id
                join fetch AddonsDishItem adi on adi.dishItems.id = di.id
                join fetch Addons a on a.id = adi.addons.id
                join fetch Dishes d on d.id = di.dishes.id
                where o.users.id = :id
                """, Orders.class);

        query.setParameter("id", id);
        List<Orders> resultList = query.setFirstResult(pageable.getOffset())
                                       .setMaxResults(pageable.getSize())
                                       .getResultList();
        return resultList;
    }

}
