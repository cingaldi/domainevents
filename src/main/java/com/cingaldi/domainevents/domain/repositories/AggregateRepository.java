package com.cingaldi.domainevents.domain.repositories;

import com.cingaldi.domainevents.domain.models.AggregateRoot;
import org.springframework.data.repository.CrudRepository;

public interface AggregateRepository extends CrudRepository<AggregateRoot , Long> {
}
