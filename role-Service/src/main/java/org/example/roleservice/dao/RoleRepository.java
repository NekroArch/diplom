package org.example.roleservice.dao;

import org.example.entities.entity.Addons;
import org.example.entities.entity.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Roles, Integer>, PagingAndSortingRepository<Roles, Integer> {
}
