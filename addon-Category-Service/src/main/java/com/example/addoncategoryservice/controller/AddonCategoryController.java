package com.example.addoncategoryservice.controller;

import com.example.addoncategoryservice.dto.AddonCategoryDto;
import com.example.addoncategoryservice.service.AddonCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addon-category")
public class AddonCategoryController {

    private final AddonCategoryService addonCategoryService;

    @Autowired
    public AddonCategoryController(AddonCategoryService addonCategoryService) {
        this.addonCategoryService = addonCategoryService;
    }


    @GetMapping
    public List<AddonCategoryDto> getAll(@PageableDefault Pageable pageable) {
        return addonCategoryService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public AddonCategoryDto getAll(@PathVariable int id) {
        return addonCategoryService.getById(id);
    }

}
