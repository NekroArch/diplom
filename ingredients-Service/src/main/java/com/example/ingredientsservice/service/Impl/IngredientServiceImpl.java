package com.example.ingredientsservice.service.Impl;

import com.example.ingredientsservice.dao.IngredientRepository;
import com.example.ingredientsservice.dto.IngredientsDto;
import com.example.ingredientsservice.mapper.IngredientMapper;
import com.example.ingredientsservice.service.IngredientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService {

    private final IngredientMapper ingredientMapper;

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientMapper dishMapper, IngredientRepository dishRepository) {
        this.ingredientMapper = dishMapper;
        this.ingredientRepository = dishRepository;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.getall.ingredient}")
    public List<IngredientsDto> getAll(Pageable pageable){
        return ingredientRepository.findAll(pageable).map(ingredientMapper::mapToIngredientsDto).stream().toList();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.get.ingredient}")
    public IngredientsDto getById(Integer id){
        return ingredientRepository.findById(id).map(ingredientMapper::mapToIngredientsDto).get();
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.save.ingredient}")
    @Transactional
    public void save(IngredientsDto ingredientsDto){
        ingredientRepository.save(ingredientMapper.mapToIngredients(ingredientsDto));
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.delete.ingredient}")
    @Transactional
    public void delete(Integer id){
        ingredientRepository.deleteById(id);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.update.ingredient}")
    @Transactional
    public void update(IngredientsDto ingredientsDto){
        ingredientRepository.save(ingredientMapper.mapToIngredients(ingredientsDto));
    }
}
