package com.cingaldi.stores_srv.presentation;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import com.cingaldi.stores_srv.domain.events.StoreOrderReceivedEvt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrdersSubscriber {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitListener(queues = "stores-srv.orders")
    public void receiveMessage(String message) {
        try {
            OrderCreatedEvt evt = new ObjectMapper().readValue(message,OrderCreatedEvt.class);
            publisher.publishEvent(new StoreOrderReceivedEvt("StoreOrder", evt.getAggregateId(), evt.getStoreId()));
        } catch (JsonProcessingException e) {
            log.warn("malformed message");
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
