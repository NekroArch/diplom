package org.example.mapper;

import org.example.dto.MenuDto;
import org.example.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuDto mapToMenuDto(Menu menu);

    Menu mapToMenu(MenuDto menuDto);

}
