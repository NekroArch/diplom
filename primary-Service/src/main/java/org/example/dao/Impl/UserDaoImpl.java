package org.example.dao.Impl;

import jakarta.persistence.TypedQuery;
import org.example.dao.UserDao;
import org.example.entity.Users;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<Users> implements UserDao {

    @Override
    protected Class<Users> getEntityClass() {
        return Users.class;
    }

//    @Override
//    public Users getUserWithFetchById(Integer id) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
//        Root<Users> o = cq.from(Users.class);
//        o.fetch("roles", JoinType.INNER);
//        o.fetch("orders", JoinType.INNER);
//        cq.select(o).where(cb.equal(o.get(Users_.id), id));
//        return entityManager.createQuery(cq).getSingleResult();
//        //переписать на два
//    }

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
