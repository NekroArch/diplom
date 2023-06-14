package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dao.DishItemDao;
import org.example.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishItemDaoImpl extends AbstractDaoImpl<DishItems> implements DishItemDao {
    @Override
    protected Class<DishItems> getEntityClass() {
        return DishItems.class;
    }

//    @Override
//    public List<DishItems> getAll() throws InterruptedException, SQLException {
//
//        TypedQuery<DishItems> q = entityManager.createQuery("""
//                SELECT
//                    di
//                FROM DishItems di
//                INNER JOIN FETCH Dishes d on d.id = di.dishes.id
//                INNER JOIN FETCH DishesIngredients di2 on di2.dish.id = d.id
//                INNER JOIN FETCH Ingredients i on i.id = di2.ingredient.id
//                """,DishItems.class);
//        List<DishItems> d = q.getResultList();
//        return d;
//    }
}
