package com.cingaldi.domainevents.domain.models;

import com.cingaldi.domainevents.domain.events.AggregateUpdatedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class AggregateRoot extends AbstractAggregateRoot<AggregateRoot> {

    @Id
    private Long id;

    public AggregateRoot() {
    }

    public AggregateRoot(Long id) {
        this.id = id;
    }

    public void executeCommand(Integer value) {
        registerEvent(new AggregateUpdatedEvent(value));
    }

    public static AggregateRoot create () {
        return new AggregateRoot(0L);
    }
}
