package org.example.service;

import org.example.dto.AbstractDto;
import org.example.dto.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface Service<D extends AbstractDto>{
    List<D> getAll(Pageable pageable) throws SQLException, InterruptedException;
    D getById(int id) throws SQLException, InterruptedException;
    D save(D entityDto) throws SQLException, InterruptedException;
    void delete(int id) throws SQLException, InterruptedException;
    D update(D entityDto) throws SQLException, InterruptedException;
}