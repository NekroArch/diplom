package org.example.dao;

import org.example.entity.Roles;
import org.example.entity.Users;

import java.sql.SQLException;

public interface UserDao extends AbstractDao<Users> {

    Users getUserByMailWithRole(String mail);

    boolean checkMail(String mail);

    Users getUserByMail(String mail);
}
