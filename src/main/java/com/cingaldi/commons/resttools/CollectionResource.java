package com.cingaldi.commons.resttools;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class CollectionResource<T> {

    @JsonProperty("result")
    private List<T> result;

    public CollectionResource(List<T> result) {
        this.result = result;
    }
}
