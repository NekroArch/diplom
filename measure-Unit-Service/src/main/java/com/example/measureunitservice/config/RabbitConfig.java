package com.example.measureunitservice.config;

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

    @Value("${rabbitmq.queue.delete.measureunit}")
    private String measureunitDeleteQueue;
    @Value("${rabbitmq.queue.save.measureunit}")
    private String measureunitSaveQueue;
    @Value("${rabbitmq.queue.update.measureunit}")
    private String measureunitUpdateQueue;
    @Value("${rabbitmq.queue.get.measureunit}")
    private String measureunitGetQueue;
    @Value("${rabbitmq.queue.getall.measureunit}")
    private String measureunitGetAllQueue;

    @Value("${routing.key.queue.measureunit.delete}")
    private String measureunitDeleteRoutingKey;
    @Value("${routing.key.queue.measureunit.save}")
    private String measureunitSaveRoutingKey;
    @Value("${routing.key.queue.measureunit.update}")
    private String measureunitUpdateRoutingKey;
    @Value("${routing.key.queue.measureunit.get}")
    private String measureunitGetRoutingKey;
    @Value("${routing.key.queue.measureunit.getall}")
    private String measureunitGetAllRoutingKey;

    @Value("${measureunit.exchange}")
    private String measureunitExchange;

    @Bean
    public DirectExchange measureunitExchange() {
        return new DirectExchange(measureunitExchange);
    }

    @Bean
    public Queue measureunitDeleteQueue() {
        return new Queue(measureunitDeleteQueue);
    }

    @Bean
    public Queue measureunitUpdateQueue() {
        return new Queue(measureunitUpdateQueue);
    }

    @Bean
    public Queue measureunitSaveQueue() {
        return new Queue(measureunitSaveQueue);
    }

    @Bean
    public Queue measureunitGetAllQueue() {
        return new Queue(measureunitGetAllQueue);
    }

    @Bean
    public Queue measureunitGetQueue() {
        return new Queue(measureunitGetQueue);
    }

    @Bean
    public Binding measureunitDeleteBinding() {
        return BindingBuilder.bind(measureunitDeleteQueue()).to(measureunitExchange()).with(measureunitDeleteRoutingKey);
    }

    @Bean
    public Binding measureunitUpdateBinding() {
        return BindingBuilder.bind(measureunitUpdateQueue()).to(measureunitExchange()).with(measureunitUpdateRoutingKey);
    }

    @Bean
    public Binding measureunitSaveBinding() {
        return BindingBuilder.bind(measureunitSaveQueue()).to(measureunitExchange()).with(measureunitSaveRoutingKey);
    }

    @Bean
    public Binding measureunitGetBinding() {
        return BindingBuilder.bind(measureunitGetQueue()).to(measureunitExchange()).with(measureunitGetRoutingKey);
    }

    @Bean
    public Binding measureunitGetAllBinding() {
        return BindingBuilder.bind(measureunitGetAllQueue()).to(measureunitExchange()).with(measureunitGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
