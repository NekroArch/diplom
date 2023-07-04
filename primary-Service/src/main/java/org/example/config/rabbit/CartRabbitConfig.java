package org.example.config.rabbit;

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
public class CartRabbitConfig {

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

    @Bean
    public DirectExchange cartExchange() {
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
        return BindingBuilder.bind(cartDeleteQueue()).to(cartExchange()).with(cartDeleteRoutingKey);
    }

    @Bean
    public Binding cartUpdateBinding() {
        return BindingBuilder.bind(cartUpdateQueue()).to(cartExchange()).with(cartUpdateRoutingKey);
    }

    @Bean
    public Binding cartSaveBinding() {
        return BindingBuilder.bind(cartSaveQueue()).to(cartExchange()).with(cartSaveRoutingKey);
    }

    @Bean
    public Binding cartGetBinding() {
        return BindingBuilder.bind(cartGetQueue()).to(cartExchange()).with(cartGetRoutingKey);
    }

    @Bean
    public Binding cartGetAllBinding() {
        return BindingBuilder.bind(cartGetAllQueue()).to(cartExchange()).with(cartGetAllRoutingKey);
    }

    @Bean
    public Binding cartGetByUserNameQueueBinding() {
        return BindingBuilder.bind(cartGetByUserNameQueue()).to(cartExchange()).with(cartGetByUserNameRoutingKey);
    }

    @Bean
    public Binding cartSaveWithUserIdQueueBinding() {
        return BindingBuilder.bind(cartSaveWithUserIdQueue()).to(cartExchange()).with(cartSaveWithUserIdRoutingKey);
    }
}
