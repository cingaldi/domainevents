package com.cingaldi.stores_srv.presentation;

import com.cingaldi.commons.resttools.CollectionResource;
import com.cingaldi.stores_srv.presentation.vm.StoreOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

@Controller
public class StoreOrdersController {


    @GetMapping("/stores/{storeId}/orders")
    @ResponseBody
    public CollectionResource getOrders(@PathVariable("storeId") Long storeId) {
        return new CollectionResource(Collections.emptyList());
    }
}
