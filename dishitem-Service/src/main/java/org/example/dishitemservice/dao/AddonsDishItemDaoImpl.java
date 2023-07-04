package org.example.dishitemservice.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.entities.entity.AddonsDishItem;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AddonsDishItemDaoImpl implements AddonsDishItemDao {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void save(AddonsDishItem addonsDishItem) {
        entityManager.persist(addonsDishItem);
    }
}
