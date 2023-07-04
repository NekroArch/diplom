package org.example.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddonRabbitConfig {
    @Value("${rabbitmq.queue.delete.addon}")
    private String addonDeleteQueue;
    @Value("${rabbitmq.queue.save.addon}")
    private String addonSaveQueue;
    @Value("${rabbitmq.queue.update.addon}")
    private String addonUpdateQueue;
    @Value("${rabbitmq.queue.get.addon}")
    private String addonGetQueue;
    @Value("${rabbitmq.queue.getall.addon}")
    private String addonGetAllQueue;

    @Value("${routing.key.queue.addon.delete}")
    private String addonDeleteRoutingKey;
    @Value("${routing.key.queue.addon.save}")
    private String addonSaveRoutingKey;
    @Value("${routing.key.queue.addon.update}")
    private String addonUpdateRoutingKey;
    @Value("${routing.key.queue.addon.get}")
    private String addonGetRoutingKey;
    @Value("${routing.key.queue.addon.getall}")
    private String addonGetAllRoutingKey;

    @Value("${addon.exchange}")
    private String addonExchange;

    @Bean
    public DirectExchange addonExchange() {
        return new DirectExchange(addonExchange);
    }

    @Bean
    public Queue addonDeleteQueue() {
        return new Queue(addonDeleteQueue);
    }

    @Bean
    public Queue addonUpdateQueue() {
        return new Queue(addonUpdateQueue);
    }

    @Bean
    public Queue addonSaveQueue() {
        return new Queue(addonSaveQueue);
    }

    @Bean
    public Queue addonGetAllQueue() {
        return new Queue(addonGetAllQueue);
    }

    @Bean
    public Queue addonGetQueue() {
        return new Queue(addonGetQueue);
    }

    @Bean
    public Binding addonDeleteBinding() {
        return BindingBuilder.bind(addonDeleteQueue()).to(addonExchange()).with(addonDeleteRoutingKey);
    }

    @Bean
    public Binding addonUpdateBinding() {
        return BindingBuilder.bind(addonUpdateQueue()).to(addonExchange()).with(addonUpdateRoutingKey);
    }

    @Bean
    public Binding addonSaveBinding() {
        return BindingBuilder.bind(addonSaveQueue()).to(addonExchange()).with(addonSaveRoutingKey);
    }

    @Bean
    public Binding addonGetBinding() {
        return BindingBuilder.bind(addonGetQueue()).to(addonExchange()).with(addonGetRoutingKey);
    }

    @Bean
    public Binding addonGetAllBinding() {
        return BindingBuilder.bind(addonGetAllQueue()).to(addonExchange()).with(addonGetAllRoutingKey);
    }
}
