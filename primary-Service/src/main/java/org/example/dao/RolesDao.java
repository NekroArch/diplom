package org.example.dao;

import org.example.entity.Roles;

import java.sql.SQLException;

public interface RolesDao extends AbstractDao<Roles> {
    Roles findByName(String name);
    Roles findRoleWithFetchById(Integer id);
}
