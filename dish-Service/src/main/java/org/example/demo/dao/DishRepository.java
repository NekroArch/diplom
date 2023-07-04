package org.example.demo.dao;

import org.example.entities.entity.Dishes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dishes, Integer>, PagingAndSortingRepository<Dishes, Integer> {
}
