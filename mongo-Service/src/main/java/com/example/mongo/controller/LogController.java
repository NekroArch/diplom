package com.example.mongo.controller;

import com.example.mongo.model.Log;
import com.example.mongo.service.LogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-mongo-log")
public class LogController {

    @Autowired
    LogInterface logInterface;

    @GetMapping
    public List<Log> getAll(){
        return logInterface.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Log> getById(@PathVariable Integer id){
        return logInterface.getById(id);
    }

    @PostMapping()
    public void save(@RequestBody Log log){
        logInterface.save(log);
    }
}
