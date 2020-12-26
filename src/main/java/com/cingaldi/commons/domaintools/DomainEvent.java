package com.cingaldi.commons.domaintools;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class DomainEvent {

    private final String aggregateType;
    private final Long aggregateId;

    public String eventType() {
        return getClass().getSimpleName();
    }

    public abstract String logMessage();
}
