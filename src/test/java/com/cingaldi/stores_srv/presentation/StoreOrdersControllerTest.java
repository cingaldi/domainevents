package com.cingaldi.stores_srv.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StoreOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public  void givenNoOrders_whenCorrectStoreId_thenReturnsEmpty () throws Exception {

        mockMvc.perform(get("/stores/0/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", empty()));
    }

}