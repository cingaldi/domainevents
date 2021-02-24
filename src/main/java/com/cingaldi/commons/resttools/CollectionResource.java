package com.cingaldi.commons.resttools;

import com.cingaldi.stores_srv.presentation.vm.StoreOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class CollectionResource {

    @JsonProperty("result")
    private List<StoreOrder> result;

    public CollectionResource(List<StoreOrder> result) {
        this.result = result;
    }
}
