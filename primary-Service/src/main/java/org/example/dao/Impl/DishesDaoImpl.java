package org.example.dao.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dao.DishesDao;
import org.example.entity.Dishes;
import org.example.entity.Dishes_;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DishesDaoImpl extends AbstractDaoImpl<Dishes> implements DishesDao {
    @Override
    protected Class<Dishes> getEntityClass() {
        return Dishes.class;
    }
}
