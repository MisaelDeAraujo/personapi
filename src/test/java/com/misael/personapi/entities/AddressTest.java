package com.misael.personapi.entities;

import com.misael.personapi.entities.enums.AddressType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AddressTest {
    @Test
    public void testEquals() {
        Address address1 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address2 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address3 = new Address(2, "Rua B", "54321-876", "456", "City", "State", AddressType.MAIN);

        assertTrue(address1.equals(address2));
        assertFalse(address1.equals(address3));
    }

    @Test
    public void testHashCode() {
        Address address1 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address2 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);

        assertEquals(address1.hashCode(), address2.hashCode());
    }
    @Test
    public void testGettersAndSetters() {
        Address address = new Address();
        address.setId(1);
        address.setPublicPlace("Rua A");
        address.setZipCode("12345-678");
        address.setNumber("123");
        address.setCity("City");
        address.setState("State");
        address.setAddressType(AddressType.MAIN);

        assertEquals(1, address.getId());
        assertEquals("Rua A", address.getPublicPlace());
        assertEquals("12345-678", address.getZipCode());
        assertEquals("123", address.getNumber());
        assertEquals("City", address.getCity());
        assertEquals("State", address.getState());
        assertEquals(AddressType.MAIN, address.getAddressType());
    }

    @Test
    public void testDataAnnotation() {
        Address address = new Address();
        address.setId(1);
        address.setPublicPlace("Rua A");
        address.setZipCode("12345-678");
        address.setNumber("123");
        address.setCity("City");
        address.setState("State");
        address.setAddressType(AddressType.MAIN);

        Address anotherAddress = new Address();
        anotherAddress.setId(1);
        anotherAddress.setPublicPlace("Rua A");
        anotherAddress.setZipCode("12345-678");
        anotherAddress.setNumber("123");
        anotherAddress.setCity("City");
        anotherAddress.setState("State");
        anotherAddress.setAddressType(AddressType.MAIN);

        assertEquals(address, anotherAddress);
    }

    @Test
    public void testBuilderAnnotation() {
        Address address = Address.builder()
                .id(1)
                .publicPlace("Rua A")
                .zipCode("12345-678")
                .number("123")
                .city("City")
                .state("State")
                .addressType(AddressType.MAIN)
                .build();

        assertNotNull(address);
        assertEquals(1, address.getId());
        assertEquals("Rua A", address.getPublicPlace());
        assertEquals("12345-678", address.getZipCode());
        assertEquals("123", address.getNumber());
        assertEquals("City", address.getCity());
        assertEquals("State", address.getState());
        assertEquals(AddressType.MAIN, address.getAddressType());
    }

    @Test
    public void testEqualsAndHashCodeAnnotation() {
        Address address1 = Address.builder()
                .id(1)
                .publicPlace("Rua A")
                .zipCode("12345-678")
                .number("123")
                .city("City")
                .state("State")
                .addressType(AddressType.MAIN)
                .build();

        Address address2 = Address.builder()
                .id(1)
                .publicPlace("Rua A")
                .zipCode("12345-678")
                .number("123")
                .city("City")
                .state("State")
                .addressType(AddressType.MAIN)
                .build();

        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

}
