package org.example.privilegesservice.dao;

import org.example.entities.entity.Privileges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesRepository extends CrudRepository<Privileges, Integer>, PagingAndSortingRepository<Privileges, Integer> {
}
