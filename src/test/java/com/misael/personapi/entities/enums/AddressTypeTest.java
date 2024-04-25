package com.misael.personapi.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTypeTest {

    @Test
    void testAddressTypeEnums() {
        assertEquals("DEFAULT", AddressType.DEFAULT.getMainAddress());
        assertEquals("MAIN", AddressType.MAIN.getMainAddress());
    }
}
