package com.cingaldi.domainevents.infrastructure;

import com.cingaldi.commons.domaintools.CrossDomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Slf4j
public class EventsAdapter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @EventListener
    public void onCrossDomainEvent(CrossDomainEvent evt) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(evt);
        log.info("publishing event => payload={}" , message);

        rabbitTemplate.convertAndSend(evt.topic(), evt.serialize());
    }
}
