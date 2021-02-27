package com.cingaldi.stores_srv.presentation;

import com.cingaldi.commons.domaintools.DomainCollectionResult;
import com.cingaldi.commons.domaintools.DomainEntityResult;
import com.cingaldi.commons.resttools.BodyVersionSwitch;
import com.cingaldi.commons.resttools.CollectionResource;
import com.cingaldi.commons.resttools.ErrorResource;
import com.cingaldi.commons.resttools.exceptions.MediaNotSupportedException;
import com.cingaldi.stores_srv.application.StoreService;
import com.cingaldi.stores_srv.application.dtos.CreateStoreDTO;
import com.cingaldi.stores_srv.application.dtos.CreateStoreOldDTO;
import com.cingaldi.stores_srv.domain.models.Store;
import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.presentation.vm.StoreOrderVM;
import com.cingaldi.stores_srv.presentation.vm.StoreOrderOldVM;
import com.cingaldi.stores_srv.presentation.vm.StoreVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class StoreOrdersController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity createStore(@RequestBody Object body, @RequestHeader("Content-Type") String contentType) {

        return new BodyVersionSwitch()
                .withVersion(contentType, "")
                .whenDefault(()->{
                    StoreVM result = storeService.createStore((CreateStoreOldDTO)body).mapTo(StoreVM::fromEntity);
                    return new ResponseEntity(result, HttpStatus.CREATED);
                })
                .orMatch("v1", ()->{
                    StoreVM result = storeService.createStore((CreateStoreDTO) body).mapTo(StoreVM::fromEntity);
                    return new ResponseEntity(result, HttpStatus.CREATED);
                })
                .getResult();
    }

    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity getOrders(@PathVariable("storeId") Long storeId, @RequestHeader("Accept") String acceptHeader) {

        DomainCollectionResult<StoreOrder> result = storeService.getOrdersFromStore(storeId);

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

    @ExceptionHandler({MediaNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ErrorResource onMediaNotSupportedException() {
        return new ErrorResource(new ErrorResource.ErrorResponse("response version not supported"));
    }
}
