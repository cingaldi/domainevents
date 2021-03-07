package com.cingaldi.stores_srv.domain.models;

import com.cingaldi.commons.resttools.exceptions.BusinessRuleViolatonException;
import com.cingaldi.stores_srv.domain.commands.AcceptOrderCmd;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.cingaldi.stores_srv.domain.models.StoreOrder.Status.ACCEPTED;
import static com.cingaldi.stores_srv.domain.models.StoreOrder.Status.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreOrderTest {

    private static final Long ORDER_ID = 0L;
    private static final Long STORE_ID = 0L;
    private static int ACCEPTANCE_EXPIRATION = 30;

    @Test
    void accept_givenOrder_whenStoreDoesntMatch_thenThrows() {

        var sut = givenOrder();

        var acceptanceTime = Instant.parse("2021-01-01T08:00:01Z");
        var command = new AcceptOrderCmd(99L, acceptanceTime, ACCEPTANCE_EXPIRATION);

        assertThrows(BusinessRuleViolatonException.class, () -> sut.accept(command));
    }

    @Test
    void accept_givenOrder_whenAcceptedInTime_thenStatusAccepted() {

        var sut = givenOrder();

        var acceptanceTime = Instant.parse("2021-01-01T08:00:01Z");
        var command = new AcceptOrderCmd(STORE_ID, acceptanceTime, ACCEPTANCE_EXPIRATION);
        
        assertThat(sut.accept(command).hasStatus(ACCEPTED)).isTrue();
        
    }

    @Test
    void accept_givenOrder_whenAcceptedAfterExpiration_thenStatusRejected() {

        var sut = givenOrder();

        var acceptanceTime = Instant.parse("2021-01-01T08:00:31Z");
        var command = new AcceptOrderCmd(STORE_ID, acceptanceTime, ACCEPTANCE_EXPIRATION);

        assertThat(sut.accept(command).hasStatus(REJECTED)).isTrue();

    }


    @Test
    void accept_givenOrder_whenAcceptedAEqualsExpiration_thenStatusAccepted() {

        var sut = givenOrder();

        var acceptanceTime = Instant.parse("2021-01-01T08:00:30Z");
        var command = new AcceptOrderCmd(STORE_ID, acceptanceTime, ACCEPTANCE_EXPIRATION);

        assertThat(sut.accept(command).hasStatus(ACCEPTED)).isTrue();

    }


    @Test
    void accept_givenOrder_whenAccepted_thenHasAcceptanceTime() {

        StoreOrder sut = givenOrder();

        var acceptanceTime = Instant.parse("2021-01-01T08:00:01Z");
        var command = new AcceptOrderCmd(STORE_ID, acceptanceTime, ACCEPTANCE_EXPIRATION);

        assertThat(sut.accept(command).acceptedAt()).isNotEmpty();

    }

    private StoreOrder givenOrder() {
        return StoreOrder.createAtTime(Instant.parse("2021-01-01T08:00:00Z"), ORDER_ID, STORE_ID);
    }

}