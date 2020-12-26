package com.cingaldi.orders_srv.domain.models;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Order extends AbstractAggregateRoot<Order> {

    @Id
    @GeneratedValue
    private Long id;

    public Order() {
    }

    public Long getId () {
        return id;
    }

    public static Order create () {
        Order order = new Order();
        order.registerEvent(new OrderCreatedEvt(order.getId()));

        return order;
    }
}
