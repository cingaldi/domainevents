package com.cingaldi.domainevents.application;

import com.cingaldi.domainevents.domain.events.AggregateUpdatedEvent;
import com.cingaldi.domainevents.domain.models.AggregateRoot;
import com.cingaldi.domainevents.domain.repositories.AggregateRepository;
import com.cingaldi.domainevents.infrastructure.DomainEventsListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class ApplicationServiceTest {

    @SpyBean
    DomainEventsListener listener;

    @SpyBean
    private ApplicationService sut;

    @Autowired
    private AggregateRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.save(new AggregateRoot(0L));
    }

    @Test
    public void shouldWork () {

        sut.mutateAggregate(0L , 1);
        verify(listener).onDomainEvent(any());
        verify(sut).onAggregateUpdated(any(AggregateUpdatedEvent.class));
    }
}