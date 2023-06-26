package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.RolesDao;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.Roles;
import org.example.entities.entity.Roles_;
import org.springframework.stereotype.Repository;

@Repository
public class RolesDaoImpl extends AbstractDaoImpl<Roles> implements RolesDao {

    @Override
    protected Class<Roles> getEntityClass() {
        return Roles.class;
    }

    @Override
    public Roles findRoleWithFetchById(Integer id) {
        TypedQuery<Roles> q = entityManager.createQuery("""
                SELECT
                    r
                FROM Roles r
                JOIN FETCH Privileges p
                where r.id = :id
                """,Roles.class);
        q.setParameter("id", id);
        Roles d = q.getSingleResult();
        return d;
    }

    @Override
    public Roles findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Roles> cq = cb.createQuery(Roles.class);
        Root<Roles> o = cq.from(Roles.class);
        cq.select(o).where(cb.equal(o.get(Roles_.NAME), name));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
