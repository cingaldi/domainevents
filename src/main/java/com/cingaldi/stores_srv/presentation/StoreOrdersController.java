package com.cingaldi.stores_srv.presentation;

import com.cingaldi.BodyVersionSwitch;
import com.cingaldi.commons.domaintools.DomainCollectionResult;
import com.cingaldi.commons.resttools.CollectionResource;
import com.cingaldi.stores_srv.application.StoreService;
import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.presentation.vm.StoreOrderVM;
import com.cingaldi.stores_srv.presentation.vm.StoreOrderOldVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StoreOrdersController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity getOrders(@PathVariable("storeId") Long storeId, @RequestHeader("Accept") String acceptHeader) {

        var result = storeService.getOrdersFromStore(storeId);

        /*
        NOTICE: you don't really need to use this method. Spring Boot provides convenient annotations to match
        Accept and Content-Type headers, but this tool works independently from the framework, plus is more flexible
        when we need to manage range of versions
         */
        return new BodyVersionSwitch()
            .withVersion(acceptHeader, "")
            .whenDefault(()->
                new ResponseEntity(new CollectionResource<>(result.mapTo(StoreOrderOldVM::fromEntity)), HttpStatus.OK)
            )
            .orMatch("v1", () ->
                new ResponseEntity(new CollectionResource<>(result.mapTo(StoreOrderVM::fromEntity)), HttpStatus.OK)
            )
            .getResult();
    }

}
