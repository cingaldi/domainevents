package com.cingaldi.orders_srv.domain.models;

import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "orders_srv_orders")
@Getter
public class Order extends AbstractAggregateRoot<Order> {

    @Id
    @GeneratedValue
    private Long id;

    private OrderState state;

    private Long storeId;

    public Order() {
    }

    public Long getId () {
        return id;
    }

    public static Order create (Long storeId) {
        Order ret = new Order();
        ret.storeId = storeId;

        return ret;
    }

    public void setCreated() {
        state = OrderState.CREATED;
        registerEvent(new OrderCreatedEvt(getId(), getStoreId()));
    }
}
