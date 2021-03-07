package com.cingaldi.stores_srv.domain.models;

import com.cingaldi.commons.domaintools.AggregateRoot;
import com.cingaldi.stores_srv.domain.services.StoreConfigurationProvider;


public class Store extends AggregateRoot {

    private String cityCode = "NAP";

    public StoreConfiguration getConfiguration(StoreConfigurationProvider provider) {
        return provider.forCity(cityCode);
    }
}
