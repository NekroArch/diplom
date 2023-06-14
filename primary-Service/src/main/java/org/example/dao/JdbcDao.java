package org.example.dao;

public interface JdbcDao {
    String getMailFormUserWithJDBCTemplateById(Integer id);

    String getMailFormUserWithJDBCById(Integer id);
}
