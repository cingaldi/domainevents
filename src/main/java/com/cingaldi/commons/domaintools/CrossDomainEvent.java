package com.cingaldi.commons.domaintools;

public interface CrossDomainEvent  extends DomainEvent{

    String topic();

    String serialize();
}
