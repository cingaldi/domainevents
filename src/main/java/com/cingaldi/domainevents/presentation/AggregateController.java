package com.cingaldi.domainevents.presentation;

import com.cingaldi.domainevents.application.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AggregateController {

    @Autowired
    ApplicationService service;

    @PostMapping("/aggregate")
    public ResponseEntity updateAggregate () {
        service.mutateAggregate(0L , 1);

        return new ResponseEntity(HttpStatus.OK);
    }
}
