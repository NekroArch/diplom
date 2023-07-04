package com.example.orderservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue.delete.order}")
    private String orderDeleteQueue;
    @Value("${rabbitmq.queue.save.order}")
    private String orderSaveQueue;
    @Value("${rabbitmq.queue.update.order}")
    private String orderUpdateQueue;
    @Value("${rabbitmq.queue.get.order}")
    private String orderGetQueue;
    @Value("${rabbitmq.queue.getall.order}")
    private String orderGetAllQueue;
    @Value("${rabbitmq.queue.saveorderforcart.order}")
    private String orderSaveOrderForCartQueue;
    @Value("${rabbitmq.queue.getorderforuser.order}")
    private String orderGetOrderForUserQueue;

    @Value("${routing.key.queue.order.delete}")
    private String orderDeleteRoutingKey;
    @Value("${routing.key.queue.order.save}")
    private String orderSaveRoutingKey;
    @Value("${routing.key.queue.order.update}")
    private String orderUpdateRoutingKey;
    @Value("${routing.key.queue.order.get}")
    private String orderGetRoutingKey;
    @Value("${routing.key.queue.order.getall}")
    private String orderGetAllRoutingKey;
    @Value("${routing.key.queue.order.saveorderforcart}")
    private String orderSaveOrderForCartRoutingKey;
    @Value("${routing.key.queue.order.getorderforuser}")
    private String orderGetOrderForUserRoutingKey;

    @Value("${order.exchange}")
    private String orderExchange;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(orderExchange);
    }

    @Bean
    public Queue orderDeleteQueue() {
        return new Queue(orderDeleteQueue);
    }

    @Bean
    public Queue orderUpdateQueue() {
        return new Queue(orderUpdateQueue);
    }

    @Bean
    public Queue orderSaveQueue() {
        return new Queue(orderSaveQueue);
    }

    @Bean
    public Queue orderGetAllQueue() {
        return new Queue(orderGetAllQueue);
    }

    @Bean
    public Queue orderSaveOrderForCartQueue() {
        return new Queue(orderSaveOrderForCartQueue);
    }

    @Bean
    public Queue orderGetQueue() {
        return new Queue(orderGetQueue);
    }

    @Bean
    public Queue orderGetOrderForUserQueue() {
        return new Queue(orderGetOrderForUserQueue);
    }

    @Bean
    public Binding orderDeleteBinding() {
        return BindingBuilder.bind(orderDeleteQueue()).to(orderExchange()).with(orderDeleteRoutingKey);
    }

    @Bean
    public Binding orderUpdateBinding() {
        return BindingBuilder.bind(orderUpdateQueue()).to(orderExchange()).with(orderUpdateRoutingKey);
    }

    @Bean
    public Binding orderSaveBinding() {
        return BindingBuilder.bind(orderSaveQueue()).to(orderExchange()).with(orderSaveRoutingKey);
    }

    @Bean
    public Binding orderGetBinding() {
        return BindingBuilder.bind(orderGetQueue()).to(orderExchange()).with(orderGetRoutingKey);
    }

    @Bean
    public Binding orderGetAllBinding() {
        return BindingBuilder.bind(orderGetAllQueue()).to(orderExchange()).with(orderGetAllRoutingKey);
    }

    @Bean
    public Binding orderSaveOrderForCartBinding() {
        return BindingBuilder.bind(orderSaveOrderForCartQueue()).to(orderExchange()).with(orderSaveOrderForCartRoutingKey);
    }

    @Bean
    public Binding orderGetOrderForUserBinding() {
        return BindingBuilder.bind(orderGetOrderForUserQueue()).to(orderExchange()).with(orderGetOrderForUserRoutingKey);
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

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
