package org.example.dao.Impl;

import org.example.dao.PrivilegesDao;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.Privileges;
import org.springframework.stereotype.Repository;

@Repository
public class PrivilegesImpl extends AbstractDaoImpl<Privileges> implements PrivilegesDao {

    @Override
    protected Class<Privileges> getEntityClass() {
        return Privileges.class;
    }
}
