package com.example.orderservice.dao;

import org.example.entities.entity.Ingredients;
import org.example.entities.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer>, PagingAndSortingRepository<Orders, Integer> {
    @Query(value = """
            insert into orders (user_id, price)
            select :id, out.price
            from (select sum(d.price * cd.quantity) as price
                from dish_items d
                join cart_dishes cd on cd.dish_item_id = d.id
                join carts c on cd.cart_id = c.id
                where c.user_id = :id) out
            returning orders.id
                    """, nativeQuery = true)
     Integer saveOrderFromCart(@Param("id") Integer id);

    @Modifying
    @Query(value = "insert into ordered_dishes (dish_item_id, order_id, quantity)\n" +
            "                SELECT cd.dish_item_id, :id, quantity\n" +
            "                FROM cart_dishes cd\n" +
            "                         join carts c on c.id = cd.cart_id\n" +
            "                         join users u on u.id = c.user_id\n" +
            "                where u.id = :userId",nativeQuery = true)
    void saveOrderedDishesRelations(@Param("userId") Integer userId, @Param("id") Integer orderId);

    List<Orders> getAllByUsers_Id(Integer id, Pageable pageable);

    @Modifying
    @Query(value = """
                delete
                from cart_dishes
                where cart_id = (select cd.cart_id
                                 FROM cart_dishes as cd
                                          join carts c on c.id = cd.cart_id
                                          join users u on u.id = c.user_id
                                 where u.id = :userId);
                """, nativeQuery = true)
    void clearCart(@Param("userId") Integer userId);
}
