package com.cingaldi.orders_srv.application;

import com.cingaldi.orders_srv.domain.models.Order;
import com.cingaldi.orders_srv.domain.repositories.OrderRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class OrdersService {

    private static Logger logger = getLogger(OrdersService.class);

    @Autowired
    private OrderRepository repository;

    @Transactional
    public void createOrder(Long storeId) {
        Order order = repository.save(Order.create(storeId));

        order.setCreated();

        repository.save(order);
    }
}
