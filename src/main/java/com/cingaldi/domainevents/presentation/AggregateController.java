package com.cingaldi.domainevents.presentation;

import com.cingaldi.domainevents.application.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AggregateController {

    @Autowired
    ApplicationService service;

    @PutMapping("/aggregate/{id}")
    public ResponseEntity updateAggregate (@PathVariable("id") Long id, @RequestBody RequestDTO req) {
        service.mutateAggregate(id , req.getValue());

        return new ResponseEntity(HttpStatus.OK);
    }

    private class RequestDTO {
        private Integer value;

        public RequestDTO() {
        }

        public RequestDTO(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
