package com.cingaldi.domainevents.domain.models;

import com.cingaldi.domainevents.domain.events.AggregateUpdatedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class AggregateRoot extends AbstractAggregateRoot<AggregateRoot> {

    @Id
    @GeneratedValue
    private Long id;

    private Integer value = 0;

    public AggregateRoot() {
    }

    public void executeCommand(Integer value) {
        this.value = value;
        registerEvent(new AggregateUpdatedEvent(id, value));
    }

    public Long getId () {
        return id;
    }

    public static AggregateRoot create () {
        return new AggregateRoot();
    }
}
