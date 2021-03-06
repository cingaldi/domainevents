package com.cingaldi.stores_srv.presentation.vm;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreOrderVM {

    @JsonProperty("order")
    private Href order;

    public  static StoreOrderVM fromEntity(StoreOrder entity) {
         return new StoreOrderVM(new Href(String.format("/orders/%d", entity.getOrderId())));
    }
}
