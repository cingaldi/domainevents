package com.cingaldi.stores_srv.presentation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class OrdersSubscriber {

    @RabbitListener(queues = {"stores-srv"})
    public void receiveMessage(Message message) {

    }
}
