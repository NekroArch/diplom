package com.example.ingredientsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue.delete.ingredient}")
    private String ingredientDeleteQueue;
    @Value("${rabbitmq.queue.save.ingredient}")
    private String ingredientSaveQueue;
    @Value("${rabbitmq.queue.update.ingredient}")
    private String ingredientUpdateQueue;
    @Value("${rabbitmq.queue.get.ingredient}")
    private String ingredientGetQueue;
    @Value("${rabbitmq.queue.getall.ingredient}")
    private String ingredientGetAllQueue;

    @Value("${routing.key.queue.ingredient.delete}")
    private String ingredientDeleteRoutingKey;
    @Value("${routing.key.queue.ingredient.save}")
    private String ingredientSaveRoutingKey;
    @Value("${routing.key.queue.ingredient.update}")
    private String ingredientUpdateRoutingKey;
    @Value("${routing.key.queue.ingredient.get}")
    private String ingredientGetRoutingKey;
    @Value("${routing.key.queue.ingredient.getall}")
    private String ingredientGetAllRoutingKey;

    @Value("${ingredient.exchange}")
    private String ingredientExchange;

    @Bean
    public DirectExchange ingredientExchange() {
        return new DirectExchange(ingredientExchange);
    }

    @Bean
    public Queue ingredientDeleteQueue() {
        return new Queue(ingredientDeleteQueue);
    }

    @Bean
    public Queue ingredientUpdateQueue() {
        return new Queue(ingredientUpdateQueue);
    }

    @Bean
    public Queue ingredientSaveQueue() {
        return new Queue(ingredientSaveQueue);
    }

    @Bean
    public Queue ingredientGetAllQueue() {
        return new Queue(ingredientGetAllQueue);
    }

    @Bean
    public Queue ingredientGetQueue() {
        return new Queue(ingredientGetQueue);
    }

    @Bean
    public Binding ingredientDeleteBinding() {
        return BindingBuilder.bind(ingredientDeleteQueue()).to(ingredientExchange()).with(ingredientDeleteRoutingKey);
    }

    @Bean
    public Binding ingredientUpdateBinding() {
        return BindingBuilder.bind(ingredientUpdateQueue()).to(ingredientExchange()).with(ingredientUpdateRoutingKey);
    }

    @Bean
    public Binding ingredientSaveBinding() {
        return BindingBuilder.bind(ingredientSaveQueue()).to(ingredientExchange()).with(ingredientSaveRoutingKey);
    }

    @Bean
    public Binding ingredientGetBinding() {
        return BindingBuilder.bind(ingredientGetQueue()).to(ingredientExchange()).with(ingredientGetRoutingKey);
    }

    @Bean
    public Binding ingredientGetAllBinding() {
        return BindingBuilder.bind(ingredientGetAllQueue()).to(ingredientExchange()).with(ingredientGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
