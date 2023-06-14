package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dao.AddonsDao;
import org.example.dto.Pageable;
import org.example.entity.Addons;
import org.example.entity.Users_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddonsDaoImpl extends AbstractDaoImpl<Addons> implements AddonsDao {

    @Override
    protected Class<Addons> getEntityClass() {
        return Addons.class;
    }

    @Override
    public Addons getAddonWithFetchById(Integer id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Addons> cq = cb.createQuery(Addons.class);
        Root<Addons> o = cq.from(Addons.class);
        cq.select(o).where(cb.equal(o.get(Users_.id), id));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Addons> findAddonsByPrice(BigDecimal price) {
        TypedQuery<Addons> q = entityManager.createQuery(
                "SELECT a FROM Addons a WHERE a.price <= :price",
                Addons.class
        );
        q.setParameter("price", price);
        return q.getResultList();
    }
}
