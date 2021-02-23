package com.cingaldi.stores_srv.infrastructure;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue() {
        return new Queue("stores-srv.orders", true);
    }

    @Bean
    Exchange exchange() {
        return new DirectExchange("amq.direct");
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding bind() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("events.aggregates.orders.created")
                .noargs();
    }
}
