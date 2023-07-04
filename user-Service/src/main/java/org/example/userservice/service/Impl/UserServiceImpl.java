package org.example.userservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.entity.Roles;
import org.example.entities.entity.Users;
import org.example.entities.utils.PageableDto;
import org.example.userservice.dao.UserRepository;
import org.example.userservice.dto.UsersDto;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.service.Impl.Exceptions.UserExistsException;
import org.example.userservice.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userDao;

    private final PasswordEncoder passwordEncoder;

    @RabbitListener(queues = "${rabbitmq.queue.getall.user}")
    @Override
    public List<UsersDto> getAll(Pageable pageable) throws SQLException, InterruptedException {
       // log.debug("Executing method getAll");
        return userDao.findAll(pageable).stream().map(userMapper::mapToUserDto).toList();
    }

    @RabbitListener(queues = "${rabbitmq.queue.get.user}")
    @Override
    public UsersDto getById(int id) throws SQLException, InterruptedException {
       // log.debug("Executing method getById with id {}", id);
        return userMapper.mapToUserDto(userDao.findById(id).get());
    }

    @RabbitListener(queues = "${rabbitmq.queue.save.user}", returnExceptions = "true")
    @Transactional
    @Override
    public UsersDto save(UsersDto entityDto) {
       // log.debug("Executing method save with {}", entityDto);

        Users users = userMapper.mapToUsers(entityDto);

        if (userDao.checkMail(entityDto.getMail())) {
            throw new UserExistsException(String.format("User with username %s exists", entityDto.getMail()));
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(List.of(Roles.builder().id(2).build()));
        Users save = userDao.save(users);
        return userMapper.mapToUserDto(save);
    }

    @RabbitListener(queues = "${rabbitmq.queue.delete.user}")
    @Transactional
    @Override
    public void delete(int id) throws SQLException, InterruptedException {
      //  log.debug("Executing method delete with id {}", id);
        userDao.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.queue.update.user}")
    @Transactional
    @Override
    public UsersDto update(UsersDto entityDto) throws SQLException, InterruptedException {
      //  log.debug("Executing method update with {}", entityDto);
        return userMapper.mapToUserDto(userDao.save(userMapper.mapToUsers(entityDto)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.getidbyusername.user}")
    @Override
    public Integer findIdByUserName(String userName) {
        return userDao.findUsersByMail(userName).getId();
    }

}
