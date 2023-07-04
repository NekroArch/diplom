package com.example.addonservice.dao;

import org.example.entities.entity.Addons;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonsRepository extends CrudRepository<Addons, Integer>, PagingAndSortingRepository<Addons, Integer> {
}
