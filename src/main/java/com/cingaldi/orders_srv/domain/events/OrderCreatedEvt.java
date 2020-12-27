package com.cingaldi.orders_srv.domain.events;

import com.cingaldi.commons.domaintools.CrossDomainEvent;

public class OrderCreatedEvt extends CrossDomainEvent {

    private Long storeId;

    public OrderCreatedEvt(Long id, Long storeId) {
        super("Order", id);
        this.storeId = storeId;
    }

    @Override
    public String topic() {
        return "events.aggregates.orders.created";
    }

    @Override
    public String logMessage() {
        return String.format("You understand this message: Created order with id=%d", getAggregateId());
    }
}
