package com.cingaldi.orders_srv.infrastructure;

import com.cingaldi.commons.domaintools.CrossDomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Slf4j
public class EventsAdapter {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void onCrossDomainEvent(CrossDomainEvent evt) throws JsonProcessingException {

        log.debug("publishing event topic={} , payload={}" , evt.topic(), evt.serialize(evt));

        rabbitTemplate.convertAndSend("amq.direct", evt.topic(), evt.serialize(evt));
    }
}
