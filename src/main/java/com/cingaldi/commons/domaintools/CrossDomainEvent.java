package com.cingaldi.commons.domaintools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CrossDomainEvent  extends DomainEvent{

    public CrossDomainEvent(String aggregateType, Long aggregateId) {
        super(aggregateType, aggregateId);
    }

    public abstract String topic();

    public static <T> String serialize(T evt) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(evt);

    }
}
