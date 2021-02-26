package com.cingaldi.stores_srv.domain.models;

import com.cingaldi.commons.domaintools.AggregateRoot;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class StoreOrder extends AggregateRoot {


    @Id
    @GeneratedValue
    private Long id;

    private Long storeId;

    public StoreOrder() {

    }

    public StoreOrder(Long orderId, Long storeId) {
        this.id = orderId;
        this.storeId = storeId;
    }
}
