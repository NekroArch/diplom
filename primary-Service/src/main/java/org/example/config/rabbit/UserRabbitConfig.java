package org.example.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConfig {

    @Value("${rabbitmq.queue.delete.user}")
    private String userDeleteQueue;
    @Value("${rabbitmq.queue.save.user}")
    private String userSaveQueue;
    @Value("${rabbitmq.queue.update.user}")
    private String userUpdateQueue;
    @Value("${rabbitmq.queue.get.user}")
    private String userGetQueue;
    @Value("${rabbitmq.queue.getall.user}")
    private String userGetAllQueue;

    @Value("${routing.key.queue.user.delete}")
    private String userDeleteRoutingKey;
    @Value("${routing.key.queue.user.save}")
    private String userSaveRoutingKey;
    @Value("${routing.key.queue.user.update}")
    private String userUpdateRoutingKey;
    @Value("${routing.key.queue.user.get}")
    private String userGetRoutingKey;
    @Value("${routing.key.queue.user.getall}")
    private String userGetAllRoutingKey;

    @Value("${user.exchange}")
    private String userExchange;

    @Bean
    public DirectExchange userExchange(){
        return new DirectExchange(userExchange);
    }

    @Bean
    public Queue userDeleteQueue(){
        return new Queue(userDeleteQueue);
    }

    @Bean
    public Binding userBinding(){
        return BindingBuilder.bind(userDeleteQueue()).to(userExchange()).with(userDeleteRoutingKey);
    }

    @Bean
    public Queue userSaveQueue(){
        return new Queue(userSaveQueue);
    }

    @Bean
    public Binding userSaveBinding(){
        return BindingBuilder.bind(userSaveQueue()).to(userExchange()).with(userSaveRoutingKey);
    }

    @Bean
    public Queue userUpdateQueue(){
        return new Queue(userUpdateQueue);
    }

    @Bean
    public Binding userUpdateBinding(){
        return BindingBuilder.bind(userUpdateQueue()).to(userExchange()).with(userUpdateRoutingKey);
    }

}
