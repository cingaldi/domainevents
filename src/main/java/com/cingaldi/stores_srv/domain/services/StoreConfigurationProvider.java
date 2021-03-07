package com.cingaldi.stores_srv.domain.services;

import com.cingaldi.stores_srv.domain.models.StoreConfiguration;
import org.springframework.stereotype.Component;

@Component
public interface StoreConfigurationProvider {

    default StoreConfiguration forCity(String cityCode) {
        return null;
    }

    default StoreConfiguration forStore(Long storeId) {
        return null;
    }
}
