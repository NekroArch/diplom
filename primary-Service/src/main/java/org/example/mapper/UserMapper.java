package org.example.mapper;

import org.example.dto.UsersDto;
import org.example.entity.Roles;
import org.example.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersDto mapToUserDto(Users user);

    Users mapToUsers(UsersDto userDto);


}
