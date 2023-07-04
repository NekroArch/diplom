package org.example.cartservice.dao;

import org.example.entities.entity.Carts;
import org.example.entities.entity.Dishes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Carts, Integer>, PagingAndSortingRepository<Carts, Integer> {
}
