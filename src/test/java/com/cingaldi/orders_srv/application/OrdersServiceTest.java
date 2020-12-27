package com.cingaldi.orders_srv.application;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import com.cingaldi.orders_srv.domain.repositories.OrderRepository;
import com.cingaldi.commons.domaintools.DomainEventsListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class OrdersServiceTest {

    @SpyBean
    DomainEventsListener listener;

    @Autowired
    private OrdersService sut;

    @Autowired
    private OrderRepository repository;

    @Test
    public void shouldWork () {

        //Given

        //When
        sut.createOrder(0L);

        //Then
        verify(listener).onDomainEvent(any(OrderCreatedEvt.class));
    }
}