package com.cingaldi.stores_srv.domain.repositories;

import com.cingaldi.stores_srv.domain.models.StoreOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreOrdersRepositoryTest {

    @Autowired
    private StoreOrdersRepository sut;

    @Test
    void findByStoreId_returnsEntity() {

        sut.save(new StoreOrder(0L, 0L));

        assertThat(sut.findByStoreId(0L).size()).isEqualTo(1);
    }
}