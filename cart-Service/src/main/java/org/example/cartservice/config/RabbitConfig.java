package org.example.cartservice.config;

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

    @Value("${rabbitmq.queue.delete.cart}")
    private String cartDeleteQueue;
    @Value("${rabbitmq.queue.save.cart}")
    private String cartSaveQueue;
    @Value("${rabbitmq.queue.update.cart}")
    private String cartUpdateQueue;
    @Value("${rabbitmq.queue.get.cart}")
    private String cartGetQueue;
    @Value("${rabbitmq.queue.getall.cart}")
    private String cartGetAllQueue;
    @Value("${rabbitmq.queue.getbyusername.cart}")
    private String cartGetByUserNameQueue;
    @Value("${rabbitmq.queue.savewithuserid.cart}")
    private String cartSaveWithUserIdQueue;

    @Value("${routing.key.queue.cart.delete}")
    private String cartDeleteRoutingKey;
    @Value("${routing.key.queue.cart.save}")
    private String cartSaveRoutingKey;
    @Value("${routing.key.queue.cart.update}")
    private String cartUpdateRoutingKey;
    @Value("${routing.key.queue.cart.get}")
    private String cartGetRoutingKey;
    @Value("${routing.key.queue.cart.getall}")
    private String cartGetAllRoutingKey;
    @Value("${routing.key.queue.cart.savewithuserid}")
    private String cartSaveWithUserIdRoutingKey;
    @Value("${routing.key.queue.cart.getbyusername}")
    private String cartGetByUserNameRoutingKey;

    @Value("${cart.exchange}")
    private String cartExchange;

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
        return new DirectExchange(cartExchange);
    }

    @Bean
    public Queue cartGetByUserNameQueue() {
        return new Queue(cartGetByUserNameQueue);
    }

    @Bean
    public Queue cartSaveWithUserIdQueue() {
        return new Queue(cartSaveWithUserIdQueue);
    }

    @Bean
    public Queue cartDeleteQueue() {
        return new Queue(cartDeleteQueue);
    }

    @Bean
    public Queue cartUpdateQueue() {
        return new Queue(cartUpdateQueue);
    }

    @Bean
    public Queue cartSaveQueue() {
        return new Queue(cartSaveQueue);
    }

    @Bean
    public Queue cartGetAllQueue() {
        return new Queue(cartGetAllQueue);
    }

    @Bean
    public Queue cartGetQueue() {
        return new Queue(cartGetQueue);
    }

    @Bean
    public Binding cartDeleteBinding() {
        return BindingBuilder.bind(cartDeleteQueue()).to(dishItemExchange()).with(cartDeleteRoutingKey);
    }

    @Bean
    public Binding cartUpdateBinding() {
        return BindingBuilder.bind(cartUpdateQueue()).to(dishItemExchange()).with(cartUpdateRoutingKey);
    }

    @Bean
    public Binding cartSaveBinding() {
        return BindingBuilder.bind(cartSaveQueue()).to(dishItemExchange()).with(cartSaveRoutingKey);
    }

    @Bean
    public Binding cartGetBinding() {
        return BindingBuilder.bind(cartGetQueue()).to(dishItemExchange()).with(cartGetRoutingKey);
    }

    @Bean
    public Binding cartGetAllBinding() {
        return BindingBuilder.bind(cartGetAllQueue()).to(dishItemExchange()).with(cartGetAllRoutingKey);
    }

    @Bean
    public Binding cartGetByUserNameQueueBinding() {
        return BindingBuilder.bind(cartGetByUserNameQueue()).to(dishItemExchange()).with(cartGetByUserNameRoutingKey);
    }

    @Bean
    public Binding cartSaveWithUserIdQueueBinding() {
        return BindingBuilder.bind(cartSaveWithUserIdQueue()).to(dishItemExchange()).with(cartSaveWithUserIdRoutingKey);
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
