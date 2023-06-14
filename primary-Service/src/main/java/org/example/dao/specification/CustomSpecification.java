package org.example.dao.specification;

import org.example.entity.Users;
import org.example.entity.Users_;
import org.springframework.data.jpa.domain.Specification;

public class CustomSpecification{

    public static Specification<Users> nameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Users_.firstName),"%" + name + "%"));
    }

    public static Specification<Users> lastNameLike(String lastName){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Users_.lastName),"%" + lastName + "%"));
    }
}
