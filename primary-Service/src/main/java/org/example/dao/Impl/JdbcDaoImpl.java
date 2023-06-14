package org.example.dao.Impl;

import org.example.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcDaoImpl implements JdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Connection connection;

    @Override
    public String getMailFormUserWithJDBCTemplateById(Integer id) {

        return jdbcTemplate.queryForObject("Select mail from users where id = ?", String.class, id);

    }

    @Override
    public String getMailFormUserWithJDBCById(Integer id) {
        String query = "Select mail from users where id = ?";

        String mail;

        try(var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            mail = resultSet.getString("mail");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mail;
    }
}
