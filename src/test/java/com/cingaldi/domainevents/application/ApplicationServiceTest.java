package com.cingaldi.domainevents.application;

import com.cingaldi.domainevents.domain.models.AggregateRoot;
import com.cingaldi.domainevents.domain.repositories.AggregateRepository;
import com.cingaldi.commons.domaintools.DomainEventsListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class ApplicationServiceTest {

    @SpyBean
    DomainEventsListener listener;

    @Autowired
    private ApplicationService sut;

    @Autowired
    private AggregateRepository repository;

    @Test
    public void shouldWork () {

        //Given
        AggregateRoot fixture = repository.save(AggregateRoot.create());

        //When
        sut.mutateAggregate(fixture.getId() , 1);

        //Then
        verify(listener).onDomainEvent(any());
    }
}