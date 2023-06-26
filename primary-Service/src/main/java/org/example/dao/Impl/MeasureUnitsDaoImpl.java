package org.example.dao.Impl;

import org.example.dao.MeasureUnitsDao;
import org.example.entities.entity.Dishes;
import org.example.entities.entity.MeasureUnits;
import org.springframework.stereotype.Repository;

@Repository
public class MeasureUnitsDaoImpl extends AbstractDaoImpl<MeasureUnits> implements MeasureUnitsDao {


    @Override
    protected Class<MeasureUnits> getEntityClass() {
        return MeasureUnits.class;
    }
}
