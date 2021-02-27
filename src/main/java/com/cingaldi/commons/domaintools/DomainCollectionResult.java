package com.cingaldi.commons.domaintools;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainCollectionResult<ENTITY> {

    private List<ENTITY> entity;

    public DomainCollectionResult(List<ENTITY> entity) {
        this.entity = entity;
    }

    public <DTO> List<DTO> mapTo(Function<ENTITY, DTO> mapper) {
        return entity
                .stream()
                .map((item) -> mapper.apply(item))
                .collect(Collectors.toList());
    }
}
