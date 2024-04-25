package com.misael.personapi.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressType {

    DEFAULT("DEFAULT"),
    MAIN("MAIN");

    private final String mainAddress;
}
