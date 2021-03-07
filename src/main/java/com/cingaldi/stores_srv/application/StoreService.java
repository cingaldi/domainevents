package com.cingaldi.stores_srv.application;

import com.cingaldi.commons.domaintools.DomainCollectionResult;
import com.cingaldi.commons.domaintools.DomainEntityResult;
import com.cingaldi.commons.resttools.exceptions.ResourceNotFoundException;
import com.cingaldi.stores_srv.application.dtos.CreateStoreDTO;
import com.cingaldi.stores_srv.application.dtos.CreateStoreOldDTO;
import com.cingaldi.stores_srv.domain.commands.AcceptOrderCmd;
import com.cingaldi.stores_srv.domain.events.StoreOrderReceivedEvt;
import com.cingaldi.stores_srv.domain.models.Store;
import com.cingaldi.stores_srv.domain.models.StoreConfiguration;
import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import com.cingaldi.stores_srv.domain.repositories.StoreRepository;
import com.cingaldi.stores_srv.domain.services.StoreConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreOrdersRepository repository;

    @Autowired
    private StoreConfigurationProvider provider;

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

        StoreConfiguration configuration = storeRepository
                .findById(storeId)
                .map(store -> store.getConfiguration(provider))
                .orElseThrow(ResourceNotFoundException::new);

        StoreOrder order = repository
                .findById(orderId)
                .map( storeOrder -> storeOrder.accept(new AcceptOrderCmd(storeId, Instant.now(), configuration.getAcceptanceExpiration())))
                .orElseThrow(ResourceNotFoundException::new);

        repository.save(order);

    }
}
