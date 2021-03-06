package com.cingaldi.commons.domaintools;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

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

    public LocalDateTime createdAt() {
        return createdAt.toInstant()
                .atZone(ZoneId.from(UTC))
                .toLocalDateTime();
    }

    public Date updatedAt() {
        return updatedAt;
    }

    protected void setCreationTime(Date creationTime) {
        createdAt = creationTime;
    }

}
