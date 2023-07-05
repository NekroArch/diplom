package org.example.privilegesservice.config;

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

    @Value("${rabbitmq.queue.delete.privileges}")
    private String privilegesDeleteQueue;
    @Value("${rabbitmq.queue.save.privileges}")
    private String privilegesSaveQueue;
    @Value("${rabbitmq.queue.update.privileges}")
    private String privilegesUpdateQueue;
    @Value("${rabbitmq.queue.get.privileges}")
    private String privilegesGetQueue;
    @Value("${rabbitmq.queue.getall.privileges}")
    private String privilegesGetAllQueue;

    @Value("${routing.key.queue.privileges.delete}")
    private String privilegesDeleteRoutingKey;
    @Value("${routing.key.queue.privileges.save}")
    private String privilegesSaveRoutingKey;
    @Value("${routing.key.queue.privileges.update}")
    private String privilegesUpdateRoutingKey;
    @Value("${routing.key.queue.privileges.get}")
    private String privilegesGetRoutingKey;
    @Value("${routing.key.queue.privileges.getall}")
    private String privilegesGetAllRoutingKey;

    @Value("${privileges.exchange}")
    private String privilegesExchange;

    @Bean
    public DirectExchange privilegesExchange() {
        return new DirectExchange(privilegesExchange);
    }

    @Bean
    public Queue privilegesDeleteQueue() {
        return new Queue(privilegesDeleteQueue);
    }

    @Bean
    public Queue privilegesUpdateQueue() {
        return new Queue(privilegesUpdateQueue);
    }

    @Bean
    public Queue privilegesSaveQueue() {
        return new Queue(privilegesSaveQueue);
    }

    @Bean
    public Queue privilegesGetAllQueue() {
        return new Queue(privilegesGetAllQueue);
    }

    @Bean
    public Queue privilegesGetQueue() {
        return new Queue(privilegesGetQueue);
    }

    @Bean
    public Binding privilegesDeleteBinding() {
        return BindingBuilder.bind(privilegesDeleteQueue()).to(privilegesExchange()).with(privilegesDeleteRoutingKey);
    }

    @Bean
    public Binding privilegesUpdateBinding() {
        return BindingBuilder.bind(privilegesUpdateQueue()).to(privilegesExchange()).with(privilegesUpdateRoutingKey);
    }

    @Bean
    public Binding privilegesSaveBinding() {
        return BindingBuilder.bind(privilegesSaveQueue()).to(privilegesExchange()).with(privilegesSaveRoutingKey);
    }

    @Bean
    public Binding privilegesGetBinding() {
        return BindingBuilder.bind(privilegesGetQueue()).to(privilegesExchange()).with(privilegesGetRoutingKey);
    }

    @Bean
    public Binding privilegesGetAllBinding() {
        return BindingBuilder.bind(privilegesGetAllQueue()).to(privilegesExchange()).with(privilegesGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
