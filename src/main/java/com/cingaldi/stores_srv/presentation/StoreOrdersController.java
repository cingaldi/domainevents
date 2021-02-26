package com.cingaldi.stores_srv.presentation;

import com.cingaldi.commons.resttools.CollectionResource;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import com.cingaldi.stores_srv.presentation.vm.StoreOrderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
public class StoreOrdersController {

    @Autowired
    StoreOrdersRepository repository;

    @GetMapping("/stores/{storeId}/orders")
    @ResponseBody
    public CollectionResource getOrders(@PathVariable("storeId") Long storeId) {
        return new CollectionResource<StoreOrderVM>(
                repository.findByStoreId(storeId)
                        .stream()
                        .map( entity -> StoreOrderVM.fromEntity(entity))
                        .collect(Collectors.toList())
                );
    }
}
