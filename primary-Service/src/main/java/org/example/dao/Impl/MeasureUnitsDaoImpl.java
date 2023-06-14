package org.example.dao.Impl;

import org.example.dao.MeasureUnitsDao;
import org.example.entity.Dishes;
import org.example.entity.MeasureUnits;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class MeasureUnitsDaoImpl extends AbstractDaoImpl<MeasureUnits> implements MeasureUnitsDao {


    @Override
    protected Class<MeasureUnits> getEntityClass() {
        return MeasureUnits.class;
    }
}
