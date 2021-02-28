package com.cingaldi.stores_srv.application;

import com.cingaldi.commons.domaintools.DomainCollectionResult;
import com.cingaldi.commons.domaintools.DomainEntityResult;
import com.cingaldi.orders_srv.domain.events.OrderCreatedEvt;
import com.cingaldi.stores_srv.application.dtos.CreateStoreDTO;
import com.cingaldi.stores_srv.application.dtos.CreateStoreOldDTO;
import com.cingaldi.stores_srv.domain.models.Store;
import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreOrdersRepository repository;

    @EventListener
    public void onOrderCreated(OrderCreatedEvt evt) {
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
}
