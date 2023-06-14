package com.example.mongo.service;

import com.example.mongo.model.Log;

import java.util.List;
import java.util.Optional;

public interface LogInterface {
    List<Log> getAll();

    Optional<Log> getById(Integer id);

    void save(Log log);
}
