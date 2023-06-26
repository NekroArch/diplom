package org.example.dao;

import org.example.entities.entity.Roles;

public interface RolesDao extends AbstractDao<Roles> {
    Roles findByName(String name);
    Roles findRoleWithFetchById(Integer id);
}
