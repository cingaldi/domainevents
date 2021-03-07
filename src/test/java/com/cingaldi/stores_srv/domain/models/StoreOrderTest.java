package com.cingaldi.stores_srv.domain.models;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.cingaldi.stores_srv.domain.models.StoreOrder.Status.ACCEPTED;
import static com.cingaldi.stores_srv.domain.models.StoreOrder.Status.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;

class StoreOrderTest {

    private static final Long ORDER_ID = 0L;
    private static final Long STORE_ID = 0L;
    private static int ACCEPTANCE_EXPIRATION = 30;

    @Test
    void accept_givenOrder_whenAcceptedInTime_thenStatusAccepted() {
        
        var creationTime = Instant.parse("2021-01-01T08:00:00Z");
        var acceptanceTime = Instant.parse("2021-01-01T08:00:01Z");
        
        var sut = StoreOrder.createAtTime(creationTime, ORDER_ID, STORE_ID);
        
        assertThat(sut.accept(acceptanceTime, ACCEPTANCE_EXPIRATION).hasStatus(ACCEPTED)).isTrue();
        
    }

    @Test
    void accept_givenOrder_whenAcceptedAfterExpiration_thenStatusRejected() {

        var creationTime = Instant.parse("2021-01-01T08:00:00Z");
        var acceptanceTime = Instant.parse("2021-01-01T08:00:31Z");

        var sut = StoreOrder.createAtTime(creationTime, ORDER_ID, STORE_ID);

        assertThat(sut.accept(acceptanceTime, ACCEPTANCE_EXPIRATION).hasStatus(REJECTED)).isTrue();

    }


    @Test
    void accept_givenOrder_whenAcceptedAEqualsExpiration_thenStatusAccepted() {

        var creationTime = Instant.parse("2021-01-01T08:00:00Z");
        var acceptanceTime = Instant.parse("2021-01-01T08:00:30Z");

        var sut = StoreOrder.createAtTime(creationTime, ORDER_ID, STORE_ID);

        assertThat(sut.accept(acceptanceTime, ACCEPTANCE_EXPIRATION).hasStatus(ACCEPTED)).isTrue();

    }


    @Test
    void accept_givenOrder_whenAccepted_thenHasAcceptanceTime() {

        var creationTime = Instant.parse("2021-01-01T08:00:00Z");

        var sut = StoreOrder.createAtTime(creationTime, ORDER_ID, STORE_ID);

        assertThat(sut.accept(creationTime, ACCEPTANCE_EXPIRATION).acceptedAt()).isNotEmpty();

    }

}