package com.cingaldi.stores_srv.domain.events;

import com.cingaldi.commons.domaintools.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class StoreOrderReceivedEvt extends DomainEvent {

    private Long storeId;

    public StoreOrderReceivedEvt(String aggregateType, Long aggregateId, Long storeId) {
        super(aggregateType, aggregateId);
        this.storeId = storeId;
    }

    @Override
    public String logMessage() {
        return String.format("received order orderId=%d to storeId=%s", getAggregateId(), getStoreId());
    }
}
