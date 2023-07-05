package com.example.statusservice.dao;

import org.example.entities.entity.Roles;
import org.example.entities.entity.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer>, PagingAndSortingRepository<Status, Integer> {
}
