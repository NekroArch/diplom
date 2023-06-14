package org.example.dao;

import org.example.dto.Pageable;
import org.example.entity.AbstractEntity;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDao<T extends AbstractEntity> {
     List<T> getAll(Pageable pageable) throws InterruptedException, SQLException;
     T getById(Integer id) throws InterruptedException, SQLException;
     T save(T entity) throws InterruptedException, SQLException;
     void delete(Integer id) throws InterruptedException, SQLException;
     T update(T entity) throws InterruptedException, SQLException;
}
