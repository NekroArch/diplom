package com.example.statusservice.contoller;

import com.example.statusservice.dto.StatusDto;
import com.example.statusservice.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }


    @GetMapping
    public List<StatusDto> getAll(@PageableDefault Pageable pageable){
        return statusService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public StatusDto getAll(@PathVariable int id){
        return statusService.getById(id);
    }
}
