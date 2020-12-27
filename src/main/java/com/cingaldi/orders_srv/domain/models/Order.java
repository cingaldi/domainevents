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

    private Long storeId;

    public Order() {
    }

    public Long getId () {
        return id;
    }

    public void initialize(Long storeId) {
        this.storeId = storeId;
        registerEvent(new OrderCreatedEvt(getId(), storeId));
    }

    public static Order create () {
        return new Order();
    }
}
