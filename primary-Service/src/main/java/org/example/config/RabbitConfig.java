package org.example.config;

import org.apache.commons.collections4.BagUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

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
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPort(port);
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

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

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());

        return container;
    }
}
