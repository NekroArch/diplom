package org.example.dao.Impl;

import org.example.dao.StatusDao;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.Status;
import org.springframework.stereotype.Repository;

@Repository
public class StatusDaoImpl extends AbstractDaoImpl<Status> implements StatusDao {


    @Override
    protected Class<Status> getEntityClass() {
        return Status.class;
    }
}
