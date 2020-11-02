package com.cingaldi.domainevents.application;

import com.cingaldi.domainevents.domain.events.AggregateUpdatedEvent;
import com.cingaldi.domainevents.domain.models.AggregateRoot;
import com.cingaldi.domainevents.domain.repositories.AggregateRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ApplicationService {

    private static Logger logger = getLogger(ApplicationService.class);

    @Autowired
    private AggregateRepository repository;

    public void mutateAggregate(Long aggregateId , Integer value) {

        AggregateRoot ar = repository.findById(aggregateId).get();

        ar.executeCommand(value);

        repository.save(ar);
    }

    @EventListener
    public void onAggregateUpdated(AggregateUpdatedEvent evt) {
        logger.info("updated aggregate with value {}" , evt.getValue());
    }
}
