package com.example.measureunitservice.dao;

import org.example.entities.entity.MeasureUnits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureUnitsRepository extends CrudRepository<MeasureUnits, Integer>, PagingAndSortingRepository<MeasureUnits, Integer> {
}
