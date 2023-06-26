package org.example.userservice.mapper;

import org.example.userservice.dto.UsersDto;
import org.example.entities.entity.Users;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersDto mapToUserDto(Users user);

    Users mapToUsers(UsersDto userDto);


}
