package org.example.dao;

import org.example.entities.entity.Users;

public interface UserDao extends AbstractDao<Users>{
    Users getUserByMailWithRole(String mail);
    Users getUserByMail(String mail);
    boolean checkMail(String mail);
}
