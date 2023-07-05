package com.example.statusservice.config;

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

    @Value("${rabbitmq.queue.delete.status}")
    private String statusDeleteQueue;
    @Value("${rabbitmq.queue.save.status}")
    private String statusSaveQueue;
    @Value("${rabbitmq.queue.update.status}")
    private String statusUpdateQueue;
    @Value("${rabbitmq.queue.get.status}")
    private String statusGetQueue;
    @Value("${rabbitmq.queue.getall.status}")
    private String statusGetAllQueue;

    @Value("${routing.key.queue.status.delete}")
    private String statusDeleteRoutingKey;
    @Value("${routing.key.queue.status.save}")
    private String statusSaveRoutingKey;
    @Value("${routing.key.queue.status.update}")
    private String statusUpdateRoutingKey;
    @Value("${routing.key.queue.status.get}")
    private String statusGetRoutingKey;
    @Value("${routing.key.queue.status.getall}")
    private String statusGetAllRoutingKey;

    @Value("${status.exchange}")
    private String statusExchange;

    @Bean
    public DirectExchange statusExchange() {
        return new DirectExchange(statusExchange);
    }

    @Bean
    public Queue statusDeleteQueue() {
        return new Queue(statusDeleteQueue);
    }

    @Bean
    public Queue statusUpdateQueue() {
        return new Queue(statusUpdateQueue);
    }

    @Bean
    public Queue statusSaveQueue() {
        return new Queue(statusSaveQueue);
    }

    @Bean
    public Queue statusGetAllQueue() {
        return new Queue(statusGetAllQueue);
    }

    @Bean
    public Queue statusGetQueue() {
        return new Queue(statusGetQueue);
    }

    @Bean
    public Binding statusDeleteBinding() {
        return BindingBuilder.bind(statusDeleteQueue()).to(statusExchange()).with(statusDeleteRoutingKey);
    }

    @Bean
    public Binding statusUpdateBinding() {
        return BindingBuilder.bind(statusUpdateQueue()).to(statusExchange()).with(statusUpdateRoutingKey);
    }

    @Bean
    public Binding statusSaveBinding() {
        return BindingBuilder.bind(statusSaveQueue()).to(statusExchange()).with(statusSaveRoutingKey);
    }

    @Bean
    public Binding statusGetBinding() {
        return BindingBuilder.bind(statusGetQueue()).to(statusExchange()).with(statusGetRoutingKey);
    }

    @Bean
    public Binding statusGetAllBinding() {
        return BindingBuilder.bind(statusGetAllQueue()).to(statusExchange()).with(statusGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
