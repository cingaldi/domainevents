package com.cingaldi.domainevents.domain.events;

import com.cingaldi.commons.domaintools.CrossDomainEvent;
import com.cingaldi.commons.domaintools.DomainEvent;

public class AggregateUpdatedEvent implements CrossDomainEvent {

    public Integer value;

    public AggregateUpdatedEvent(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String type() {
        return "AggregateUpdated";
    }

    @Override
    public String topic() {
        return "events.aggregates.aggregate.updated";
    }

    @Override
    public String serialize() {
        return String.format("{\"value\": %d}", value);
    }
}
