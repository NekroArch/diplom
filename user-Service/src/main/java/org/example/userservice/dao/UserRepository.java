package org.example.userservice.dao;

import org.example.entities.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer>, PagingAndSortingRepository<Users, Integer> {
    @Query(value = "SELECT case when count(u.mail) > 0 then true else false end from Users u where u.mail = :mail")
    boolean checkMail(@Param("mail") String mail);

    @Query(value = "select u.firstName, u.lastName, u.mail, u.phone, u.password from Users u where u.id = :id")
    Users findUserByIdWithoutRolesAndOrders(Integer id);

    Users findUsersByMail(String username);
}
