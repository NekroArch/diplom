package org.example.roleservice.contoller;

import org.example.roleservice.dto.RolesDto;
import org.example.roleservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public List<RolesDto> getAll(@PageableDefault Pageable pageable){
        return roleService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public RolesDto getAll(@PathVariable int id){
        return roleService.getById(id);
    }
}
