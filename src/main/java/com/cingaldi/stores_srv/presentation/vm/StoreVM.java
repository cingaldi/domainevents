package com.cingaldi.stores_srv.presentation.vm;

import com.cingaldi.stores_srv.domain.models.Store;

public class StoreVM {

    public static StoreVM fromEntity(Store store){
        return new StoreVM();
    }
}
