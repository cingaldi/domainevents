package com.cingaldi.commons.domaintools;

import java.util.function.Function;

public class DomainEntityResult <ENTITY>{

    private ENTITY entity;

    public DomainEntityResult(ENTITY entity) {
        this.entity = entity;
    }

    public <DTO> DTO mapTo(Function<ENTITY, DTO> mapper) {
        return mapper.apply(entity);
    }
}
