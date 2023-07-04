package org.example.roleservice.config;

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

    @Value("${rabbitmq.queue.delete.role}")
    private String roleDeleteQueue;
    @Value("${rabbitmq.queue.save.role}")
    private String roleSaveQueue;
    @Value("${rabbitmq.queue.update.role}")
    private String roleUpdateQueue;
    @Value("${rabbitmq.queue.get.role}")
    private String roleGetQueue;
    @Value("${rabbitmq.queue.getall.role}")
    private String roleGetAllQueue;

    @Value("${routing.key.queue.role.delete}")
    private String roleDeleteRoutingKey;
    @Value("${routing.key.queue.role.save}")
    private String roleSaveRoutingKey;
    @Value("${routing.key.queue.role.update}")
    private String roleUpdateRoutingKey;
    @Value("${routing.key.queue.role.get}")
    private String roleGetRoutingKey;
    @Value("${routing.key.queue.role.getall}")
    private String roleGetAllRoutingKey;

    @Value("${role.exchange}")
    private String roleExchange;

    @Bean
    public DirectExchange roleExchange() {
        return new DirectExchange(roleExchange);
    }

    @Bean
    public Queue roleDeleteQueue() {
        return new Queue(roleDeleteQueue);
    }

    @Bean
    public Queue roleUpdateQueue() {
        return new Queue(roleUpdateQueue);
    }

    @Bean
    public Queue roleSaveQueue() {
        return new Queue(roleSaveQueue);
    }

    @Bean
    public Queue roleGetAllQueue() {
        return new Queue(roleGetAllQueue);
    }

    @Bean
    public Queue roleGetQueue() {
        return new Queue(roleGetQueue);
    }

    @Bean
    public Binding roleDeleteBinding() {
        return BindingBuilder.bind(roleDeleteQueue()).to(roleExchange()).with(roleDeleteRoutingKey);
    }

    @Bean
    public Binding roleUpdateBinding() {
        return BindingBuilder.bind(roleUpdateQueue()).to(roleExchange()).with(roleUpdateRoutingKey);
    }

    @Bean
    public Binding roleSaveBinding() {
        return BindingBuilder.bind(roleSaveQueue()).to(roleExchange()).with(roleSaveRoutingKey);
    }

    @Bean
    public Binding roleGetBinding() {
        return BindingBuilder.bind(roleGetQueue()).to(roleExchange()).with(roleGetRoutingKey);
    }

    @Bean
    public Binding roleGetAllBinding() {
        return BindingBuilder.bind(roleGetAllQueue()).to(roleExchange()).with(roleGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
