package com.cingaldi.orders_srv.domain.repositories;

import com.cingaldi.orders_srv.domain.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
