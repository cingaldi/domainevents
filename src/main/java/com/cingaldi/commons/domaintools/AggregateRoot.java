package com.cingaldi.commons.domaintools;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AggregateRoot extends AbstractAggregateRoot {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updatedAt;

    private boolean deleted;

    public AggregateRoot() {
        this.deleted = false;
    }

    public AggregateRoot(Long id) {
        this();
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public Date createdAt() {
        return createdAt;
    }

    public Date updatedAt() {
        return updatedAt;
    }

}
