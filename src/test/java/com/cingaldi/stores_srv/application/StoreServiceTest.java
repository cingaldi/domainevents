package com.cingaldi.stores_srv.application;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StoreServiceTest {

    @Mock
    StoreOrdersRepository repository;

    @InjectMocks
    StoreService sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void acceptOrder_givenStore_whenOrderAccepted_thenSaves() {

        when(repository.findById(0L)).thenReturn(
                Optional.of(StoreOrder.createAtTime(Instant.now(), 0L, 0L))
        );

        sut.acceptOrder(0L, 0L);

        verify(repository).save(any());
    }

}