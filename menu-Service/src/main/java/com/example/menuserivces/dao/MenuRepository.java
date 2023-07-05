package com.example.menuserivces.dao;

import org.example.entities.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer>, PagingAndSortingRepository<Menu, Integer> {
    @Query(value = "SELECT m, d from Menu as m JOIN Dishes as d where m.name=:name")
    Menu findDishesInMenuByName(@Param("name") String name);
}
