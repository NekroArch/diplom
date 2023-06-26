package org.example.mapper;

import org.example.dto.UsersDto;
import org.example.entities.entity.Roles;
import org.example.entities.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersDto mapToUserDto(Users user);

    Users mapToUsers(UsersDto userDto);


}
