package org.example.dishitemservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue.delete.dishitem}")
    private String dishItemDeleteQueue;
    @Value("${rabbitmq.queue.save.dishitem}")
    private String dishItemSaveQueue;
    @Value("${rabbitmq.queue.update.dishitem}")
    private String dishItemUpdateQueue;
    @Value("${rabbitmq.queue.get.dishitem}")
    private String dishItemGetQueue;
    @Value("${rabbitmq.queue.getall.dishitem}")
    private String dishItemGetAllQueue;
    @Value("${rabbitmq.queue.saveforcart.dishitem}")
    private String dishItemSaveForCartQueue;

    @Value("${routing.key.queue.dishitem.delete}")
    private String dishItemDeleteRoutingKey;
    @Value("${routing.key.queue.dishitem.save}")
    private String dishItemSaveRoutingKey;
    @Value("${routing.key.queue.dishitem.update}")
    private String dishItemUpdateRoutingKey;
    @Value("${routing.key.queue.dishitem.get}")
    private String dishItemGetRoutingKey;
    @Value("${routing.key.queue.dishitem.getall}")
    private String dishItemGetAllRoutingKey;
    @Value("${routing.key.queue.dishitem.saveforcart}")
    private String dishItemSaveForCartRoutingKey;

    @Value("${dishitem.exchange}")
    private String dishItemExchange;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public DirectExchange dishItemExchange() {
        return new DirectExchange(dishItemExchange);
    }

    @Bean
    public Queue dishItemDeleteQueue() {
        return new Queue(dishItemDeleteQueue);
    }

    @Bean
    public Queue dishItemUpdateQueue() {
        return new Queue(dishItemUpdateQueue);
    }

    @Bean
    public Queue dishItemSaveQueue() {
        return new Queue(dishItemSaveQueue);
    }

    @Bean
    public Queue dishItemGetAllQueue() {
        return new Queue(dishItemGetAllQueue);
    }

    @Bean
    public Queue dishItemGetQueue() {
        return new Queue(dishItemGetQueue);
    }

    @Bean
    public Queue dishItemSaveForCartQueue() {
        return new Queue(dishItemSaveForCartQueue);
    }

    @Bean
    public Binding dishItemDeleteBinding() {
        return BindingBuilder.bind(dishItemDeleteQueue()).to(dishItemExchange()).with(dishItemDeleteRoutingKey);
    }

    @Bean
    public Binding dishItemUpdateBinding() {
        return BindingBuilder.bind(dishItemUpdateQueue()).to(dishItemExchange()).with(dishItemUpdateRoutingKey);
    }

    @Bean
    public Binding dishItemSaveBinding() {
        return BindingBuilder.bind(dishItemSaveQueue()).to(dishItemExchange()).with(dishItemSaveRoutingKey);
    }

    @Bean
    public Binding dishItemGetBinding() {
        return BindingBuilder.bind(dishItemGetQueue()).to(dishItemExchange()).with(dishItemGetRoutingKey);
    }

    @Bean
    public Binding dishItemGetAllBinding() {
        return BindingBuilder.bind(dishItemGetAllQueue()).to(dishItemExchange()).with(dishItemGetAllRoutingKey);
    }

    @Bean
    public Binding dishItemSaveForCartBinding() {
        return BindingBuilder.bind(dishItemSaveForCartQueue()).to(dishItemExchange()).with(dishItemSaveForCartRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPort(port);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
