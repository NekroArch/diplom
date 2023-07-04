package org.example.dishitemservice.dao;

import org.example.entities.entity.DishItems;
import org.example.entities.entity.DishItems_;
import org.example.entities.entity.Dishes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishItemRepository extends CrudRepository<DishItems, Integer>, PagingAndSortingRepository<DishItems, Integer> {
}
