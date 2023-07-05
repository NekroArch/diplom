package com.example.addoncategoryservice.service.Impl;

import com.example.addoncategoryservice.dao.AddonCategoryRepository;
import com.example.addoncategoryservice.dto.AddonCategoryDto;
import com.example.addoncategoryservice.mapper.AddonCategoryMapper;
import com.example.addoncategoryservice.service.AddonCategoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddonCategoryServiceImpl implements AddonCategoryService {

    private final AddonCategoryMapper addonCategoryMapper;

    private final AddonCategoryRepository addonCategoryRepository;

    @Autowired
    public AddonCategoryServiceImpl(AddonCategoryMapper addonCategoryMapper, AddonCategoryRepository addonCategoryRepository) {
        this.addonCategoryMapper = addonCategoryMapper;
        this.addonCategoryRepository = addonCategoryRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.addoncategory}")
    public List<AddonCategoryDto> getAll(Pageable pageable){
        return addonCategoryRepository.findAll(pageable).map(addonCategoryMapper::mapToAddonsCategoryDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.addoncategory}")
    public AddonCategoryDto getById(Integer id){
        return addonCategoryRepository.findById(id).map(addonCategoryMapper::mapToAddonsCategoryDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.addoncategory}")
    @Transactional
    public void save(AddonCategoryDto ingredientsDto){
        addonCategoryRepository.save(addonCategoryMapper.mapToAddonsCategory(ingredientsDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.addoncategory}")
    @Transactional
    public void delete(Integer id){
        addonCategoryRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.addoncategory}")
    @Transactional
    public void update(AddonCategoryDto ingredientsDto){
        addonCategoryRepository.save(addonCategoryMapper.mapToAddonsCategory(ingredientsDto));
    }
}
