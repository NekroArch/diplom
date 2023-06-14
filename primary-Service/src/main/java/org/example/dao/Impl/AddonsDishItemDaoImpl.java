package org.example.dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.example.dao.AddonsDao;
import org.example.dao.AddonsDishItemDao;
import org.example.entity.Addons;
import org.example.entity.AddonsDishItem;
import org.springframework.stereotype.Component;
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
