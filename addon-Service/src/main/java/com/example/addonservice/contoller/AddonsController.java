package com.example.addonservice.contoller;

import com.example.addonservice.dto.AddonsDto;
import com.example.addonservice.service.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addons")
public class AddonsController {

    private final AddonService addonService;

    @Autowired
    public AddonsController(AddonService addonService) {
        this.addonService = addonService;
    }


    @GetMapping
    public List<AddonsDto> getAll(@PageableDefault Pageable pageable){
        return addonService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public AddonsDto getAll(@PathVariable int id){
        return addonService.getById(id);
    }
}
