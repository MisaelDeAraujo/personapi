package com.misael.personapi.entities;

import com.misael.personapi.entities.enums.AddressType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    @Test
    public void testEquals() {
        Person person1 = new Person(1, "John Doe", "1990-01-01", null);
        Person person2 = new Person(1, "John Doe", "1990-01-01", null);
        Person person3 = new Person(2, "Jane Smith", "1985-05-05", null);

        assertTrue(person1.equals(person2));
        assertFalse(person1.equals(person3));
    }

    @Test
    public void testHashCode() {
        Person person1 = new Person(1, "John Doe", "1990-01-01", null);
        Person person2 = new Person(1, "John Doe", "1990-01-01", null);

        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void testEqualsWithAddress() {
        Address address1 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address2 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address3 = new Address(2, "Rua B", "54321-876", "456", "City", "State", AddressType.MAIN);

        Person person1 = new Person(1, "John Doe", "1990-01-01", List.of(address1));
        Person person2 = new Person(1, "John Doe", "1990-01-01", List.of(address2));
        Person person3 = new Person(2, "Jane Smith", "1985-05-05", List.of(address3));

        assertTrue(person1.equals(person2));
        assertFalse(person1.equals(person3));
    }

    @Test
    public void testHashCodeWithAddress() {
        Address address1 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);
        Address address2 = new Address(1, "Rua A", "12345-678", "123", "City", "State", AddressType.MAIN);

        Person person1 = new Person(1, "John Doe", "1990-01-01", List.of(address1));
        Person person2 = new Person(1, "John Doe", "1990-01-01", List.of(address2));

        assertEquals(person1.hashCode(), person2.hashCode());
    }
}
