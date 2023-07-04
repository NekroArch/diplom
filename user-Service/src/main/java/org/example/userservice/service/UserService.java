package org.example.userservice.service;

import org.example.userservice.dto.UsersDto;

public interface UserService extends Service<UsersDto> {
    Integer findIdByUserName(String userName);
}
