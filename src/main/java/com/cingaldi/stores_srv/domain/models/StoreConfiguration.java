package com.cingaldi.stores_srv.domain.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StoreConfiguration {
    private final int acceptanceExpiration;
}
