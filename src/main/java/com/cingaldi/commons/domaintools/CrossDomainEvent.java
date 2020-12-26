package com.cingaldi.commons.domaintools;

public abstract class CrossDomainEvent  extends DomainEvent{

    public CrossDomainEvent(String aggregateType, Long aggregateId) {
        super(aggregateType, aggregateId);
    }

    public abstract String topic();
}
