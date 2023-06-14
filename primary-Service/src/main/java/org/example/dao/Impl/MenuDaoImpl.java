package org.example.dao.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.dao.MenuDao;
import org.example.entity.Dishes_;
import org.example.entity.Menu;
import org.example.entity.Menu_;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDaoImpl extends AbstractDaoImpl<Menu> implements MenuDao {

    @Override
    protected Class<Menu> getEntityClass() {
        return Menu.class;
    }
    @Override
    public Menu findDishesInMenuByName(String name){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
        Root<Menu> o = cq.from(Menu.class);
        o.fetch(Menu_.DISHES, JoinType.INNER);
        cq.select(o).where(cb.equal(o.get(Menu_.NAME), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
