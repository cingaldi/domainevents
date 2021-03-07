package com.cingaldi.stores_srv.domain.repositories;

import com.cingaldi.stores_srv.domain.models.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Long> {
}
