package com.example.menuserivces.service.Impl;

import com.example.menuserivces.dao.MenuRepository;
import com.example.menuserivces.dto.MenuDto;
import com.example.menuserivces.mapper.MenuMapper;
import com.example.menuserivces.service.MenuService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final MenuRepository menuRepository;


    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, MenuRepository menuRepository) {
        this.menuMapper = menuMapper;
        this.menuRepository = menuRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.menu}")
    public List<MenuDto> getAll(Pageable pageable){
        return menuRepository.findAll(pageable).map(menuMapper::mapToMenuDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.menu}")
    public MenuDto getById(Integer id){
        return menuRepository.findById(id).map(menuMapper::mapToMenuDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.menu}")
    @Transactional
    public void save(MenuDto menusDto){
        menuRepository.save(menuMapper.mapToMenu(menusDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.menu}")
    @Transactional
    public void delete(Integer id){
        menuRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.menu}")
    @Transactional
    public void update(MenuDto menusDto){
        menuRepository.save(menuMapper.mapToMenu(menusDto));
    }

    @RabbitListener(queues = "${rabbitmq.queue.getbyname.menu}")
    @Override
    public MenuDto getDishInMenuByName(String name) {
        return menuMapper.mapToMenuDto(menuRepository.findDishesInMenuByName(name));
    }
}
