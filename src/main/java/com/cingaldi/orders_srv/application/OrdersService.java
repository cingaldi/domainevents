package com.cingaldi.orders_srv.application;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import com.cingaldi.orders_srv.domain.models.Order;
import com.cingaldi.orders_srv.domain.repositories.OrderRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class OrdersService {

    private static Logger logger = getLogger(OrdersService.class);

    @Autowired
    private OrderRepository repository;

    public void createOrder() {
        repository.save(Order.create());
    }
}
