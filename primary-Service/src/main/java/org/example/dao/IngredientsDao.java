package org.example.dao;

import org.example.entity.Ingredients;

public interface IngredientsDao extends AbstractDao<Ingredients> {
    Ingredients getIngredientsWithFetchById(Integer id);
    Ingredients getIngredientByName(String name);
}
