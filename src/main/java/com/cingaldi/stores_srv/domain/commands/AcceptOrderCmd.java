package com.cingaldi.stores_srv.domain.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class AcceptOrderCmd {

    private final Long storeId;
    private final Instant now;
    private final int acceptanceExpiration;

}
