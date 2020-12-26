package com.cingaldi.orders_srv.domain.events;

import com.cingaldi.commons.domaintools.CrossDomainEvent;

public class OrderCreatedEvt extends CrossDomainEvent {

    public OrderCreatedEvt(Long id) {
        super("AggregateRoot", id);
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
