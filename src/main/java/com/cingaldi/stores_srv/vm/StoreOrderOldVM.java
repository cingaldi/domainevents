package com.cingaldi.stores_srv.vm;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreOrderOldVM {

    @JsonProperty("orderId")
    private Long orderId;

    public StoreOrderOldVM() {

    }

    public static StoreOrderOldVM fromEntity(StoreOrder entity) {
        var ret = new StoreOrderOldVM();
        ret.orderId = entity.getId();

        return ret;
    }

}