package org.example.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.DishesDao;
import org.example.entities.entity.Dishes;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DishesDaoImpl extends AbstractDaoImpl<Dishes> implements DishesDao {
    @Override
    protected Class<Dishes> getEntityClass() {
        return Dishes.class;
    }
}
