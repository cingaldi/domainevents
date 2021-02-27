package com.cingaldi.stores_srv;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrdersSubscriber {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitListener(queues = "stores-srv.orders")
    public void receiveMessage(OrderCreatedEvt message) {
        publisher.publishEvent(message);
    }
}
