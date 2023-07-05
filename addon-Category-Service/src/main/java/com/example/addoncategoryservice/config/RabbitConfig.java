package com.example.addoncategoryservice.config;

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

    @Value("${rabbitmq.queue.delete.addoncategory}")
    private String addonCategoryDeleteQueue;
    @Value("${rabbitmq.queue.save.addoncategory}")
    private String addonCategorySaveQueue;
    @Value("${rabbitmq.queue.update.addoncategory}")
    private String addonCategoryUpdateQueue;
    @Value("${rabbitmq.queue.get.addoncategory}")
    private String addonCategoryGetQueue;
    @Value("${rabbitmq.queue.getall.addoncategory}")
    private String addonCategoryGetAllQueue;

    @Value("${routing.key.queue.addoncategory.delete}")
    private String addonCategoryDeleteRoutingKey;
    @Value("${routing.key.queue.addoncategory.save}")
    private String addonCategorySaveRoutingKey;
    @Value("${routing.key.queue.addoncategory.update}")
    private String addonCategoryUpdateRoutingKey;
    @Value("${routing.key.queue.addoncategory.get}")
    private String addonCategoryGetRoutingKey;
    @Value("${routing.key.queue.addoncategory.getall}")
    private String addonCategoryGetAllRoutingKey;

    @Value("${addoncategory.exchange}")
    private String addonCategoryExchange;

    @Bean
    public DirectExchange addonCategoryExchange() {
        return new DirectExchange(addonCategoryExchange);
    }

    @Bean
    public Queue addoncategoryDeleteQueue() {
        return new Queue(addonCategoryDeleteQueue);
    }

    @Bean
    public Queue addoncategoryUpdateQueue() {
        return new Queue(addonCategoryUpdateQueue);
    }

    @Bean
    public Queue addoncategorySaveQueue() {
        return new Queue(addonCategorySaveQueue);
    }

    @Bean
    public Queue addoncategoryGetAllQueue() {
        return new Queue(addonCategoryGetAllQueue);
    }

    @Bean
    public Queue addoncategoryGetQueue() {
        return new Queue(addonCategoryGetQueue);
    }

    @Bean
    public Binding addoncategoryDeleteBinding() {
        return BindingBuilder.bind(addoncategoryDeleteQueue()).to(addonCategoryExchange()).with(addonCategoryDeleteRoutingKey);
    }

    @Bean
    public Binding addoncategoryUpdateBinding() {
        return BindingBuilder.bind(addoncategoryUpdateQueue()).to(addonCategoryExchange()).with(addonCategoryUpdateRoutingKey);
    }

    @Bean
    public Binding addoncategorySaveBinding() {
        return BindingBuilder.bind(addoncategorySaveQueue()).to(addonCategoryExchange()).with(addonCategorySaveRoutingKey);
    }

    @Bean
    public Binding addoncategoryGetBinding() {
        return BindingBuilder.bind(addoncategoryGetQueue()).to(addonCategoryExchange()).with(addonCategoryGetRoutingKey);
    }

    @Bean
    public Binding addoncategoryGetAllBinding() {
        return BindingBuilder.bind(addoncategoryGetAllQueue()).to(addonCategoryExchange()).with(addonCategoryGetAllRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
