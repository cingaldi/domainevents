package com.cingaldi.orders_srv.presentation;

import com.cingaldi.orders_srv.application.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrdersController {

    @Autowired
    OrdersService service;

    @PostMapping("/orders")
    public ResponseEntity createOrder(@RequestBody CreateOrderRequest req) {
        service.createOrder();

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public static class CreateOrderRequest {
        public CreateOrderRequest() {
        }
    }
}
