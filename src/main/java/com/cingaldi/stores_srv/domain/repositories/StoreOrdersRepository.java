package com.cingaldi.stores_srv.domain.repositories;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreOrdersRepository extends CrudRepository<StoreOrder, Long> {

    List<StoreOrder> findByStoreId(Long storeId);
}
