package com.example.menuserivces.config;

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

    @Value("${rabbitmq.queue.delete.menu}")
    private String menuDeleteQueue;
    @Value("${rabbitmq.queue.save.menu}")
    private String menuSaveQueue;
    @Value("${rabbitmq.queue.update.menu}")
    private String menuUpdateQueue;
    @Value("${rabbitmq.queue.get.menu}")
    private String menuGetQueue;
    @Value("${rabbitmq.queue.getall.menu}")
    private String menuGetAllQueue;
    @Value("${rabbitmq.queue.getbyname.menu}")
    private String menuGetByNameQueue;

    @Value("${routing.key.queue.menu.delete}")
    private String menuDeleteRoutingKey;
    @Value("${routing.key.queue.menu.save}")
    private String menuSaveRoutingKey;
    @Value("${routing.key.queue.menu.update}")
    private String menuUpdateRoutingKey;
    @Value("${routing.key.queue.menu.get}")
    private String menuGetRoutingKey;
    @Value("${routing.key.queue.menu.getall}")
    private String menuGetAllRoutingKey;
    @Value("${routing.key.queue.menu.getbyname}")
    private String menuGetByNameRoutingKey;

    @Value("${menu.exchange}")
    private String menuExchange;

    @Bean
    public DirectExchange menuExchange() {
        return new DirectExchange(menuExchange);
    }

    @Bean
    public Queue menuDeleteQueue() {
        return new Queue(menuDeleteQueue);
    }

    @Bean
    public Queue menuUpdateQueue() {
        return new Queue(menuUpdateQueue);
    }

    @Bean
    public Queue menuSaveQueue() {
        return new Queue(menuSaveQueue);
    }

    @Bean
    public Queue menuGetAllQueue() {
        return new Queue(menuGetAllQueue);
    }

    @Bean
    public Queue menuGetQueue() {
        return new Queue(menuGetQueue);
    }

    @Bean
    public Queue menuGetByNameQueue() {
        return new Queue(menuGetByNameQueue);
    }

    @Bean
    public Binding menuDeleteBinding() {
        return BindingBuilder.bind(menuDeleteQueue()).to(menuExchange()).with(menuDeleteRoutingKey);
    }

    @Bean
    public Binding menuUpdateBinding() {
        return BindingBuilder.bind(menuUpdateQueue()).to(menuExchange()).with(menuUpdateRoutingKey);
    }

    @Bean
    public Binding menuSaveBinding() {
        return BindingBuilder.bind(menuSaveQueue()).to(menuExchange()).with(menuSaveRoutingKey);
    }

    @Bean
    public Binding menuGetBinding() {
        return BindingBuilder.bind(menuGetQueue()).to(menuExchange()).with(menuGetRoutingKey);
    }

    @Bean
    public Binding menuGetAllBinding() {
        return BindingBuilder.bind(menuGetAllQueue()).to(menuExchange()).with(menuGetAllRoutingKey);
    }

    @Bean
    public Binding menuGetByNameBinding() {
        return BindingBuilder.bind(menuGetByNameQueue()).to(menuExchange()).with(menuGetByNameRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
