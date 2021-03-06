package com.cingaldi.stores_srv.domain.models;

import com.cingaldi.commons.domaintools.AggregateRoot;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.SECONDS;

@Entity
@Getter
public class StoreOrder extends AggregateRoot {

    private Long orderId;
    private Long storeId;
    private Status orderStatus = Status.PENDING;
    private LocalDateTime acceptedAt;

    public StoreOrder() {

    }

    public StoreOrder(Long orderId, Long storeId) {
        this.orderId = orderId;
        this.storeId = storeId;
    }

    public StoreOrder accept(Instant now) {
        Instant expiration = createdAt().plus(30, SECONDS).toInstant(UTC);

        if (now.isAfter(expiration)) {
            orderStatus = Status.REJECTED;
            return this;
        }

        acceptedAt = LocalDateTime.ofInstant(now, UTC);
        orderStatus = Status.ACCEPTED;
        return this;
    }

    public boolean hasStatus(Status status) {
        return orderStatus.equals(status);
    }

    public static StoreOrder createAtTime(Instant creationTime, Long orderId, Long storeId) {
        var ret = new StoreOrder(orderId, storeId);

        //that's how you should manage bWcomp
        ret.setCreationTime(Date.from(creationTime));

        return ret;
    }

    public Optional<LocalDateTime> acceptedAt() {
        return Optional.ofNullable(acceptedAt);
    }

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
