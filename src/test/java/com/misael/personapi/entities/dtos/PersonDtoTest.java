package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.enums.AddressType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonDtoTest {

    @Test
    void testPersonDtoConstruction() {
        Integer id = 1;
        String completeName = "John Doe";
        String birthDay = "1990-01-01";
        String publicPlace = "123 Main St";
        String zipCode = "12345";
        String number = "1A";
        String city = "City";
        String state = "State";
        AddressType addressType = AddressType.MAIN;

        PersonDto personDto = PersonDto.builder()
                .id(id)
                .completeName(completeName)
                .birthDay(birthDay)
                .publicPlace(publicPlace)
                .zipCode(zipCode)
                .number(number)
                .city(city)
                .state(state)
                .addressType(addressType)
                .build();

        assertEquals(id, personDto.id());
        assertEquals(completeName, personDto.completeName());
        assertEquals(birthDay, personDto.birthDay());
        assertEquals(publicPlace, personDto.publicPlace());
        assertEquals(zipCode, personDto.zipCode());
        assertEquals(number, personDto.number());
        assertEquals(city, personDto.city());
        assertEquals(state, personDto.state());
        assertEquals(addressType, personDto.addressType());
    }
}
