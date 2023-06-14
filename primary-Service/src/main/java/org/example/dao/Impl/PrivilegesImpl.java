package org.example.dao.Impl;

import org.example.dao.PrivilegesDao;
import org.example.entity.Dishes;
import org.example.entity.Privileges;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class PrivilegesImpl extends AbstractDaoImpl<Privileges> implements PrivilegesDao {

    @Override
    protected Class<Privileges> getEntityClass() {
        return Privileges.class;
    }
}
