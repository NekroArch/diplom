package org.example.demo.config;

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

    @Value("${rabbitmq.queue.delete.dish}")
    private String dishDeleteQueue;
    @Value("${rabbitmq.queue.save.dish}")
    private String dishSaveQueue;
    @Value("${rabbitmq.queue.update.dish}")
    private String dishUpdateQueue;
    @Value("${rabbitmq.queue.get.dish}")
    private String dishGetQueue;
    @Value("${rabbitmq.queue.getall.dish}")
    private String dishGetAllQueue;

    @Value("${routing.key.queue.dish.delete}")
    private String dishDeleteRoutingKey;
    @Value("${routing.key.queue.dish.save}")
    private String dishSaveRoutingKey;
    @Value("${routing.key.queue.dish.update}")
    private String dishUpdateRoutingKey;
    @Value("${routing.key.queue.dish.get}")
    private String dishGetRoutingKey;
    @Value("${routing.key.queue.dish.getall}")
    private String dishGetAllRoutingKey;

    @Value("${dish.exchange}")
    private String dishExchange;

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(dishExchange);
    }

    @Bean
    public Queue dishDeleteQueue() {
        return new Queue(dishDeleteQueue);
    }

    @Bean
    public Queue dishUpdateQueue() {
        return new Queue(dishUpdateQueue);
    }

    @Bean
    public Queue dishSaveQueue() {
        return new Queue(dishSaveQueue);
    }

    @Bean
    public Queue dishGetAllQueue() {
        return new Queue(dishGetAllQueue);
    }

    @Bean
    public Queue dishGetQueue() {
        return new Queue(dishGetQueue);
    }

    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(dishDeleteQueue()).to(userExchange()).with(dishDeleteRoutingKey);
    }

    @Bean
    public Binding userUpdateBinding() {
        return BindingBuilder.bind(dishUpdateQueue()).to(userExchange()).with(dishUpdateRoutingKey);
    }

    @Bean
    public Binding userSaveBinding() {
        return BindingBuilder.bind(dishSaveQueue()).to(userExchange()).with(dishSaveRoutingKey);
    }

    @Bean
    public Binding userGetBinding() {
        return BindingBuilder.bind(dishGetQueue()).to(userExchange()).with(dishGetRoutingKey);
    }

    @Bean
    public Binding userGetAllBinding() {
        return BindingBuilder.bind(dishGetAllQueue()).to(userExchange()).with(dishGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
