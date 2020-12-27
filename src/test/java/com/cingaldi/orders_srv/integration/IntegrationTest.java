package com.cingaldi.orders_srv.integration;

import com.cingaldi.orders_srv.domain.models.Order;
import com.cingaldi.orders_srv.domain.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        repository.save(Order.create());
    }

    @Test
    public void givenAggregate_whenUpdate_thenPublishEvent() throws Exception {

        mockMvc.perform(
            post("/orders").contentType(MediaType.APPLICATION_JSON).content("{\"storeId\":1}")
        )
         .andExpect(
            status().isAccepted()
         );

        await().atMost(200, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            rabbitTemplate.receive("test");
        });
    }

    @TestConfiguration
    public static class RabbitConfig {

        @Bean
        public Binding bind() {
            return BindingBuilder
                    .bind(new Queue("test" , false))
                    .to(new DirectExchange("amq.direct"))
                    .with("events.aggregates.aggregate.updated");
        }
    }
}
