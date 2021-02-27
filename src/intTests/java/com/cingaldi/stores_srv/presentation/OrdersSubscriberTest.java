package com.cingaldi.stores_srv.presentation;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import com.cingaldi.stores_srv.OrdersSubscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class OrdersSubscriberTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitAdmin admin;

    @MockBean
    ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    public void setUp() {
        admin.purgeQueue("stores-srv.orders");
    }

    @Test
    public void whenMessageReceived_thenSubscriberInvoked() throws JsonProcessingException {

        OrderCreatedEvt event = new OrderCreatedEvt(0L, 1L);
        String body = new ObjectMapper().writeValueAsString(event);

        rabbitTemplate.convertAndSend("amq.direct", event.topic(), body);

        await().atMost(2 , TimeUnit.SECONDS).untilAsserted(() -> {
            verify(applicationEventPublisher).publishEvent(any());
        });
    }

}