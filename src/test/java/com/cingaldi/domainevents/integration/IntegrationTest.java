package com.cingaldi.domainevents.integration;

import com.cingaldi.domainevents.domain.events.AggregateUpdatedEvent;
import com.cingaldi.domainevents.domain.models.AggregateRoot;
import com.cingaldi.domainevents.domain.repositories.AggregateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    AggregateRepository repository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        repository.save(AggregateRoot.create());
    }

    @Test
    public void givenAggregate_whenUpdate_thenPublishEvent() throws Exception {

        mockMvc.perform(post("/aggregate"))
                .andExpect(status().isOk());

        await().atMost(200, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            rabbitTemplate.receive("test");
        });
    }

    @TestConfiguration
    public static class RabbitConfig {

        @Bean
        public Exchange topicExchage () {
            return new TopicExchange("amq.topic");
        }

        @Bean
        public Queue queue() {
            return new Queue("test" , false);
        }

        @Bean
        public Binding bind() {
            return BindingBuilder.bind(queue()).to(topicExchage()).with("events.aggregates.aggregate.updated").noargs();
        }
    }
}
