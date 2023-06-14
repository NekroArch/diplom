package org.example.dao.Impl;

import org.example.dao.StatusDao;
import org.example.entity.Dishes;
import org.example.entity.Status;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class StatusDaoImpl extends AbstractDaoImpl<Status> implements StatusDao {


    @Override
    protected Class<Status> getEntityClass() {
        return Status.class;
    }
}
