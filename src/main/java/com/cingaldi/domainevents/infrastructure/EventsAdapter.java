package com.cingaldi.domainevents.infrastructure;

import com.cingaldi.commons.domaintools.CrossDomainEvent;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class EventsAdapter {

    private static Logger log = getLogger("EventsAdapter");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    public void onCrossDomainEvent(CrossDomainEvent evt) {
        log.info("publishing event of type {} with routing key {}" , evt.type(), evt.topic());
        rabbitTemplate.convertAndSend(evt.topic(), evt.serialize());
    }
}
