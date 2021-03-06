package com.cingaldi.stores_srv.application;

import com.cingaldi.commons.domaintools.DomainCollectionResult;
import com.cingaldi.commons.domaintools.DomainEntityResult;
import com.cingaldi.stores_srv.application.dtos.CreateStoreDTO;
import com.cingaldi.stores_srv.application.dtos.CreateStoreOldDTO;
import com.cingaldi.stores_srv.domain.events.StoreOrderReceivedEvt;
import com.cingaldi.stores_srv.domain.models.Store;
import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;


@Service
public class StoreService {

    @Autowired
    private StoreOrdersRepository repository;

    @EventListener
    public void onOrderCreated(StoreOrderReceivedEvt evt) {
        repository.save(new StoreOrder(evt.getAggregateId(), evt.getStoreId()));
    }

    public DomainEntityResult<Store> createStore(CreateStoreOldDTO createStore) {
        return null;
    }

    public DomainEntityResult<Store> createStore(CreateStoreDTO createStore) {
        return null;
    }

    public DomainCollectionResult<StoreOrder> getOrdersFromStore(Long storeId) {
        return new DomainCollectionResult<>(repository.findByStoreId(storeId));
    }

    public void acceptOrder(Long storeId, Long orderId) {

        repository.findById(orderId).ifPresent( storeOrder -> {
            storeOrder.accept(Instant.now());
            repository.save(storeOrder);
        });
    }
}
