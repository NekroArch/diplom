package com.example.mongo.service.impl;

import com.example.mongo.model.Log;
import com.example.mongo.repository.LogRepository;
import com.example.mongo.service.LogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService implements LogInterface {

    @Autowired
    LogRepository logRepository;

    @Override
    public List<Log> getAll() {
        return logRepository.findAll();
    }

    @Override
    public Optional<Log> getById(Integer id) {
        return logRepository.findById(id);
    }

    @Override
    public void save(Log log) {
        logRepository.save(log);
    }
}
