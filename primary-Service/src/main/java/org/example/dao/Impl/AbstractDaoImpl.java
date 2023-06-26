package org.example.dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.AbstractDao;
import org.example.entities.entity.AbstractEntity;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;


public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<T> getEntityClass();


    @Override
    public List<T> getAll(Pageable pageable) throws InterruptedException, SQLException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        return entityManager.createQuery(query)
                            .setFirstResult(pageable.getPageNumber())
                            .setMaxResults(pageable.getPageSize())
                            .getResultList();
    }

    @Override
    public T getById(Integer id) throws InterruptedException, SQLException {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public T save(T entity) throws InterruptedException, SQLException {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Integer id) throws InterruptedException, SQLException {
        entityManager.remove(getById(id));
    }

    @Override
    public T update(T entity) throws InterruptedException, SQLException {
        entityManager.merge(entity);
        return entity;
    }
}
