package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import org.example.dao.UserDao;
import org.example.entities.entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<Users> implements UserDao {

    @Override
    protected Class<Users> getEntityClass() {
        return Users.class;
    }

    @Override
    public Users getUserByMail(String mail) {
        return entityManager.createQuery("""
                select u from Users u
                where u.mail = :mail
                """, Users.class).setParameter("mail", mail).getSingleResult();
    }

    @Override
    public Users getUserByMailWithRole(String mail) {

        return entityManager.createQuery("""
                select u from Users u
                JOIN FETCH u.roles as r
                where u.mail = :mail
                """, Users.class).setParameter("mail", mail).getSingleResult();

    }

    @Override
    public boolean checkMail(String mail) {
        TypedQuery<Boolean> query = entityManager.createQuery("""
                SELECT case when count(u.mail) > 0 then true else false end from Users u where u.mail = :mail
                """, Boolean.class);
        query.setParameter("mail", mail);
        return query.getSingleResult();
    }


}
