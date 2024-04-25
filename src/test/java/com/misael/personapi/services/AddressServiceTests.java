package com.misael.personapi.services;

import com.misael.personapi.entities.Address;
import com.misael.personapi.entities.Person;
import com.misael.personapi.entities.dtos.AddressDto;
import com.misael.personapi.entities.dtos.AddressRequestDto;
import com.misael.personapi.entities.dtos.AddressUpdateDto;
import com.misael.personapi.entities.enums.AddressType;
import com.misael.personapi.exceptions.PersonNotFoundException;
import com.misael.personapi.repositories.AddressRepository;
import com.misael.personapi.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void shouldListAllAddresses() {
        Address address1 = Address.builder()
                .id(1)
                .publicPlace("Public Place 1")
                .zipCode("123456")
                .number("123")
                .city("City 1")
                .state("State 1")
                .addressType(AddressType.DEFAULT)
                .build();
        Address address2 = Address.builder()
                .id(2)
                .publicPlace("Public Place 2")
                .zipCode("789012")
                .number("456")
                .city("City 2")
                .state("State 2")
                .addressType(AddressType.MAIN)
                .build();
        List<Address> addressList = List.of(address1, address2);

        when(addressRepository.findAll()).thenReturn(addressList);

        List<AddressDto> result = addressService.listAllAddress();

        assertEquals(2, result.size());

        assertEquals(address1.getId(), result.get(0).addressId());
        assertEquals(address1.getPublicPlace(), result.get(0).publicPlace());
        assertEquals(address1.getZipCode(), result.get(0).zipCode());
        assertEquals(address1.getNumber(), result.get(0).number());
        assertEquals(address1.getCity(), result.get(0).city());
        assertEquals(address1.getState(), result.get(0).state());
        assertEquals(address1.getAddressType(), result.get(0).addressType());

        assertEquals(address2.getId(), result.get(1).addressId());
        assertEquals(address2.getPublicPlace(), result.get(1).publicPlace());
        assertEquals(address2.getZipCode(), result.get(1).zipCode());
        assertEquals(address2.getNumber(), result.get(1).number());
        assertEquals(address2.getCity(), result.get(1).city());
        assertEquals(address2.getState(), result.get(1).state());
        assertEquals(address2.getAddressType(), result.get(1).addressType());
    }

    @Test
    void shouldCreateNewAddress() {

        AddressRequestDto addressRequestDto = AddressRequestDto.builder()
                .publicPlace("Rua ABC")
                .zipCode("12345678")
                .number("123")
                .city("São Paulo")
                .state("SP")
                .addressType(AddressType.MAIN)
                .build();

        when(personRepository.findById(anyInt())).thenReturn(Optional.of(new Person()));
        when(addressRepository.findByNumber(anyString())).thenReturn(Optional.empty());

        AddressDto result = addressService.crateNewAddress(1, addressRequestDto);

        assertNotNull(result);

    }

    @Test
    void shouldThrowPersonNotFoundExceptionWhenCreatingNewAddressForNonexistentPerson() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        AddressRequestDto addressRequestDto = AddressRequestDto.builder()
                .publicPlace("Rua ABC")
                .zipCode("12345678")
                .number("123")
                .city("São Paulo")
                .state("SP")
                .addressType(AddressType.MAIN)
                .build();

        assertThrows(PersonNotFoundException.class, () -> addressService.crateNewAddress(1, addressRequestDto));
    }

    @Test
    void shouldUpdateAddressAndSetMainAddressType() {
        int personId = 1;
        int addressId = 1;
        AddressUpdateDto updateDto = AddressUpdateDto.builder()
                .publicPlace("New Public Place")
                .zipCode("New Zip Code")
                .number("New Number")
                .city("New City")
                .state("New State")
                .addressType(AddressType.MAIN)
                .build();

        Person existingPerson = new Person();
        Address existingAddress = Address.builder()
                .id(addressId)
                .publicPlace("Old Public Place")
                .zipCode("Old Zip Code")
                .number("Old Number")
                .city("Old City")
                .state("Old State")
                .addressType(AddressType.DEFAULT)
                .build();
        existingPerson.setAddresses(List.of(existingAddress));

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));

        ArgumentCaptor<Person> personCaptorBefore = ArgumentCaptor.forClass(Person.class);
        ArgumentCaptor<Person> personCaptorAfter = ArgumentCaptor.forClass(Person.class);

        AddressDto updatedAddress = addressService.updateAddress(personId, addressId, updateDto);

        assertNotNull(updatedAddress);

        assertEquals(AddressType.MAIN, existingAddress.getAddressType());

        verify(personRepository, times(1)).save(personCaptorBefore.capture());
        verify(personRepository, times(1)).save(personCaptorAfter.capture());

        Person personBefore = personCaptorBefore.getValue();
        Person personAfter = personCaptorAfter.getValue();

        assertTrue(personAfter.getAddresses().contains(existingAddress));
    }



}
