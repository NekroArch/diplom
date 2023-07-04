package com.example.ingredientsservice.dao;

import org.example.entities.entity.Dishes;
import org.example.entities.entity.Ingredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredients, Integer>, PagingAndSortingRepository<Ingredients, Integer> {
}
