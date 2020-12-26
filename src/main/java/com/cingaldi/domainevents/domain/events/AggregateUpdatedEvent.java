package com.cingaldi.domainevents.domain.events;

import com.cingaldi.commons.domaintools.CrossDomainEvent;
import com.cingaldi.commons.domaintools.DomainEvent;

public class AggregateUpdatedEvent extends CrossDomainEvent {

    public Integer value;

    public AggregateUpdatedEvent(Long id, Integer value) {
        super("AggregateRoot", id);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String topic() {
        return "events.aggregates.aggregate.updated";
    }

    @Override
    public String serialize() {
        return String.format("{\"value\": %d}", value);
    }

    @Override
    public String logMessage() {
        return String.format("human readable message: update aggregate with value %d", value);
    }
}
