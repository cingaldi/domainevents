package com.cingaldi.stores_srv.presentation.vm;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreOrderVM {

    @JsonProperty("orderId")
    private Long orderId;

    public StoreOrderVM () {

    }

    public static StoreOrderVM fromEntity(StoreOrder entity) {
        var ret = new StoreOrderVM();
        ret.orderId = entity.getId();

        return ret;
    }

}
