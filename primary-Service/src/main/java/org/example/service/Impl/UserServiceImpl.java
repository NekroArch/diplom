package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserDao;
import org.example.dto.UsersDto;
import org.example.entities.entity.Privileges;
import org.example.entities.entity.Roles;
import org.example.entities.entity.Users;
import org.example.entities.utils.PageableDto;
import org.example.service.UserService;
import org.example.util.myUser.MyUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${routing.key.queue.user.delete}")
    private String userDeleteRoutingKey;
    @Value("${routing.key.queue.user.update}")
    private String userUpdateRoutingKey;
    @Value("${routing.key.queue.user.save}")
    private String userSaveRoutingKey;

    @Value("${user.exchange}")
    private String userExchange;

    private final UserDao userDao;

    private final RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) {

        log.debug("Executing method loadUserByUsername with username {}", username);

        Users user;
        try {
            user = userDao.getUserByMailWithRole(username);
        } catch (Exception e) {
            throw new BadCredentialsException(String.format("User with mail %s not found", username));
        }

        return new MyUser(
                user.getId(),
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

    @Override
    public List<UsersDto> getAll(Pageable pageable) {
//        PageableDto pageableDto = new PageableDto(pageable.getPageNumber(), pageable.getPageSize());
//        return rabbitTemplate.convertSendAndReceiveAsType(
//                userExchange,
//                userGetAllRoutingKey,
//                pageableDto,
//                new ParameterizedTypeReference<List<UsersDto>>() {
//                }
//        );

        ArrayList n = new ArrayList<>(
                Arrays.asList(
                        Objects.requireNonNull(restTemplate.getForObject("http://"+ "USER" + "/users?page={page}&size={size}",
                                UsersDto[].class,
                                pageable.getPageNumber(), pageable.getPageSize())
                        )
                )
        );
        return n;
    }

    @Override
    public UsersDto getById(int id) throws SQLException, InterruptedException {
//        UsersDto usersDto = rabbitTemplate.convertSendAndReceiveAsType(
//                userExchange,
//                userGetRoutingKey,
//                id,
//                new ParameterizedTypeReference<UsersDto>() {}
//        );

        UsersDto forObject = restTemplate.getForObject("http://"+ "USER" + "/users/{id}", UsersDto.class, id);
        return forObject;
    }

    @Override
    public UsersDto save(UsersDto entityDto) {
        return rabbitTemplate.convertSendAndReceiveAsType(
                userExchange,
                userSaveRoutingKey,
                entityDto,
                new ParameterizedTypeReference<UsersDto>() {
                }
        );
    }

    @Override
    public void delete(int id) throws SQLException, InterruptedException {
        rabbitTemplate.convertAndSend(userExchange, userDeleteRoutingKey, id);
    }

    @Override
    public UsersDto update(UsersDto entityDto) throws SQLException, InterruptedException {
        return rabbitTemplate.convertSendAndReceiveAsType(
                userExchange,
                userUpdateRoutingKey,
                entityDto,
                new ParameterizedTypeReference<UsersDto>() {
                }
        );
    }
}
