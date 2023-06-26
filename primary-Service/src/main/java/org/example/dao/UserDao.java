package org.example.dao;

import org.example.entities.entity.Roles;
import org.example.entities.entity.Users;

public interface UserDao extends AbstractDao<Users> {

    Users getUserByMailWithRole(String mail);

    boolean checkMail(String mail);

    Users getUserByMail(String mail);
}
