package com.example.addoncategoryservice.dao;

import org.example.entities.entity.AddonsCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonCategoryRepository extends CrudRepository<AddonsCategory, Integer>, PagingAndSortingRepository<AddonsCategory, Integer> {
}
