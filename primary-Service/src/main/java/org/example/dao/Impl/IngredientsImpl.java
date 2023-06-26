package org.example.dao.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dao.IngredientsDao;
import org.example.entities.entity.Ingredients;
import org.example.entities.entity.*;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IngredientsImpl extends AbstractDaoImpl<Ingredients> implements IngredientsDao {
    @Override
    protected Class<Ingredients> getEntityClass() {
        return Ingredients.class;
    }

    @Override
    public Ingredients getIngredientsWithFetchById(Integer id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredients> cq = cb.createQuery(Ingredients.class);
        Root<Ingredients> o = cq.from(Ingredients.class);
        o.fetch("measureUnits", JoinType.INNER);
        cq.select(o).where(cb.equal(o.get(Users_.id), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public Ingredients getIngredientByName(String name){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredients> cq = cb.createQuery(Ingredients.class);
        Root<Ingredients> o = cq.from(Ingredients.class);
        cq.select(o).where(cb.equal(o.get(Ingredients_.NAME), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
