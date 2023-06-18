package org.example.dao;

import org.example.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJPADao extends PagingAndSortingRepository<Users, Integer> {

    Users findUsersById(Integer id);

    @Query(value = "SELECT * FROM users where id =:id", nativeQuery = true)
    Users findUsersByIdWithNativeQuery(@Param("id") Integer id);

    @Query(value = "SELECT u FROM Users u where u.id =:id")
    Users findUsersByIdWithJPQLQuery(@Param("id") Integer id);

//    @Query(value = "SELECT phone, mail FROM users where mail =:mail", nativeQuery = true)
//    UserView findByMail(@Param("mail") String mail);

    @EntityGraph(attributePaths = "roles")
    Users findUsersByMail(String mail);

    @EntityGraph(value = "User.orders")
    Users findUsersByFirstName(String firstName);

    List<Users> findAllByFirstName(String name, Pageable pageable);

    List<Users> findAll(Specification<Users> specification);
}
