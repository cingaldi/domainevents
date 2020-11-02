package com.cingaldi.domainevents.infrastructure;

import com.cingaldi.commons.domaintools.DomainEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DomainEventsListener {

    private static Logger log = getLogger("DomainEvent");

    @EventListener
    @Order(-1)
    public void onDomainEvent(DomainEvent evt) {
        log.info("sent domain event of type {}", evt.type());
    }
}
