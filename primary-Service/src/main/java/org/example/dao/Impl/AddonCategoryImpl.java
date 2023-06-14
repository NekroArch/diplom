package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import org.example.dao.AddonsCategoryDao;
import org.example.dto.Pageable;
import org.example.entity.AddonsCategory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class AddonCategoryImpl extends AbstractDaoImpl<AddonsCategory> implements AddonsCategoryDao {

    @Override
    protected Class<AddonsCategory> getEntityClass() {
        return AddonsCategory.class;
    }

    @Override
    public AddonsCategory getAddonsInCategoryByCategoryName(String name) {
        TypedQuery<AddonsCategory> q = entityManager.createQuery(
                "SELECT ac FROM AddonsCategory ac JOIN FETCH Addons WHERE ac.name = :name",
                    AddonsCategory.class
        );
        q.setParameter("name", name);
        return q.getSingleResult();
    }
}
