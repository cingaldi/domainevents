package com.cingaldi.stores_srv.presentation;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import com.cingaldi.stores_srv.domain.repositories.StoreOrdersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StoreOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StoreOrdersRepository repository;

    @Test
    public  void givenNoOrders_whenCorrectStoreId_thenReturnsEmpty () throws Exception {

        mockMvc.perform(
            get("/stores/0/orders")
            .accept("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", empty()));
    }


    @Test
    public void givenOneOrder_whenCorrectStoreId_thenReturnsOrder () throws Exception {

        var fixture = repository.save(new StoreOrder(0L, 0L));

        mockMvc.perform(
            get("/stores/0/orders")
            .accept("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].orderId", is((fixture.getOrderId().intValue()))));
    }


    @Test
    public void givenOneOrder_whenNewVersion_thenReturnsLink () throws Exception {

        var fixture = repository.save(new StoreOrder(0L, 0L));

        mockMvc.perform(
                get("/stores/0/orders")
                        .accept("application/vnd.com.cingaldi.v1+json")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result[0].order.href", containsString("/orders/")));
    }


    @Test
    public void givenOneOrder_whenWrongVersion_thenError415 () throws Exception {

        var fixture = repository.save(new StoreOrder(0L, 0L));

        mockMvc.perform(
                get("/stores/0/orders")
                        .accept("application/vnd.com.cingaldi.v2+json")
        )
        .andExpect(status().isUnsupportedMediaType());
    }
}