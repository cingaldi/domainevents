package com.cingaldi.commons.domaintools;

import com.cingaldi.commons.domaintools.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DomainEventsListener {

    @EventListener
    @Order(-1)
    public void onDomainEvent(DomainEvent evt) {
        log.debug("sent domain event type={} , aggregate={} , id={}", evt.eventType(), evt.getAggregateType(), evt.getAggregateId());
        log.info(evt.logMessage());
    }
}
