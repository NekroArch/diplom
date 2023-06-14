package org.example.service.Impl;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserDao;
import org.example.dto.Pageable;
import org.example.dto.UsersDto;
import org.example.entity.Privileges;
import org.example.entity.Roles;
import org.example.entity.Users;
import org.example.mapper.UserMapper;
import org.example.service.Impl.Exceptions.UserExistsException;
import org.example.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsersDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
        log.debug("Executing method getAll");
        return userDao.getAll(pageable).stream().map(userMapper::mapToUserDto).toList();
    }

    @Override
    public UsersDto getById(int id) throws SQLException, InterruptedException {
        log.debug("Executing method getById with id {}", id);
        return userMapper.mapToUserDto(userDao.getById(id));
    }

    @Transactional
    @Override
    public UsersDto save(UsersDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method save with {}", entityDto);

        Users users = userMapper.mapToUsers(entityDto);

        if (userDao.checkMail(entityDto.getMail())) {
            throw new UserExistsException(String.format("User with username %s exists", entityDto.getMail()));
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(List.of(Roles.builder().id(1).build()));
        Users save = userDao.save(users);
        return userMapper.mapToUserDto(save);
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        log.debug("Executing method delete with id {}", id);
        userDao.delete(id);
    }

    @Transactional
    @Override
    public UsersDto update(UsersDto entityDto) throws SQLException, InterruptedException {
        log.debug("Executing method update with {}", entityDto);
        return userMapper.mapToUserDto(userDao.update(userMapper.mapToUsers(entityDto)));
    }


    @Override
    public UserDetails loadUserByUsername(String username) {

        log.debug("Executing method loadUserByUsername with username {}", username);

        Users user;
        try {
            user = userDao.getUserByMailWithRole(username);
        } catch (NoResultException e) {
            throw new BadCredentialsException(String.format("User with mail %s not found", username));
        }

        return new User(
                user.getMail(),
                user.getPassword(),
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Roles> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privileges> collection = new ArrayList<>();
        for (Roles role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privileges item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
