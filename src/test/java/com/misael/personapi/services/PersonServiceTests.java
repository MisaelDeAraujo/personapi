package com.misael.personapi.services;

import com.misael.personapi.entities.Address;
import com.misael.personapi.entities.Person;
import com.misael.personapi.entities.dtos.AddressDto;
import com.misael.personapi.entities.dtos.PersonRequestDto;
import com.misael.personapi.entities.dtos.PersonUpdateDto;
import com.misael.personapi.entities.dtos.PersonWithAddressListDto;
import com.misael.personapi.entities.enums.AddressType;
import com.misael.personapi.exceptions.PersonNotFoundException;
import com.misael.personapi.repositories.AddressRepository;
import com.misael.personapi.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ZipCodeValidatorService zipCodeValidatorService;

    @InjectMocks
    private PersonService personService;


    @Test
    public void shouldReturnPersonWithAddressList() {
        Address address = Address.builder()
                .id(1)
                .publicPlace("place test")
                .zipCode("zip code")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Person person = Person.builder()
                .id(1)
                .completeName("person test")
                .birthday("birthday test")
                .addresses(addresses)
                .build();

        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        assertFalse(personService.listAllPersonsWithAddress().isEmpty());
    }

    @Test
    void shouldFindPersonById() {
        Address address = Address.builder()
                .id(1)
                .publicPlace("place test")
                .zipCode("zip code")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Person person = Person.builder()
                .id(1)
                .completeName("person test")
                .birthday("birthday test")
                .addresses(addresses)
                .build();

        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        PersonWithAddressListDto result = personService.findPersonById(1);

        assertNotNull(result);
        assertEquals(person.getId(), result.id());
        assertEquals(person.getCompleteName(), result.completeName());
        assertEquals(person.getBirthday(), result.birthDay());

        // Verificar se a lista de endereços contém o endereço esperado
        assertEquals(1, result.addresses().size()); // Verifica se há apenas um endereço na lista
        Address addressFromDto = result.addresses().get(0); // Pega o primeiro endereço da lista

        assertEquals(address.getId(), addressFromDto.getId());
        assertEquals(address.getPublicPlace(), addressFromDto.getPublicPlace());
        assertEquals(address.getZipCode(), addressFromDto.getZipCode());
        assertEquals(address.getNumber(), addressFromDto.getNumber());
        assertEquals(address.getCity(), addressFromDto.getCity());
        assertEquals(address.getState(), addressFromDto.getState());
        assertEquals(address.getAddressType(), addressFromDto.getAddressType());
    }




    @Test
    void shouldThrowPersonNotFoundExceptionWhenPersonNotFound() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            personService.findPersonById(1);
        });
    }


    @Test
    void shouldSaveExistingPersonWithNewAddress(){
        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("bd test")
                .publicPlace("public place test")
                .zipCode("zc test")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        personService.cratePerson(dto);

        PersonRequestDto dto2 = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("bd test")
                .publicPlace("public place test2")
                .zipCode("zc test2")
                .number("number test2")
                .city("city test2")
                .state("state test2")
                .addressType(AddressType.DEFAULT)
                .build();
        personService.cratePerson(dto2);

        verify(personRepository,times(2)).save(any());

    }

    @Test
    void shouldCreateNewPersonWithUniqueAddress() {
        when(addressRepository.findByZipCode(anyString())).thenReturn(Optional.empty());

        when(personRepository.findByCompleteName(anyString())).thenReturn(Optional.empty());

        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("new person")
                .birthDay("bd test")
                .publicPlace("public place test")
                .zipCode("unique zip code")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        personService.cratePerson(dto);

        verify(personRepository, times(1)).save(any());
    }

    @Test
    void shouldCreatePersonWithExistingZipCode(){
        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("bd test")
                .publicPlace("public place test")
                .zipCode("zc test")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        personService.cratePerson(dto);
        PersonRequestDto dto2 = PersonRequestDto.builder()
                .completeName("person test2")
                .birthDay("bd test2")
                .publicPlace("public place test2")
                .zipCode("zc test")
                .number("numbertest2")
                .city("city test2")
                .state("state test2")
                .addressType(AddressType.DEFAULT)
                .build();
        personService.cratePerson(dto2);
        verify(personRepository,times(2)).save(any());

    }
    @Test
    void shouldCreateNewAddressMainWithExistingAddressMainOnPersonList(){
        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("bd test")
                .publicPlace("public place test")
                .zipCode("zc test")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        personService.cratePerson(dto);
        PersonRequestDto dto2 = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("bd test")
                .publicPlace("public place test2")
                .zipCode("zc test2")
                .number("number test2")
                .city("city test2")
                .state("state test2")
                .addressType(AddressType.MAIN)
                .build();
        personService.cratePerson(dto2);
        verify(personRepository,times(2)).save(any());
    }

    @Test
    void shouldUpdatePerson() {
        Address address = Address.builder()
                .publicPlace("place test")
                .zipCode("zip code")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Person person = Person.builder()
                .id(1)
                .completeName("person test")
                .birthday("birthday test")
                .addresses(addresses)
                .build();

        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        PersonUpdateDto dto = PersonUpdateDto.builder()
                .completeName("person test update")
                .birthDay("birthday test update")
                .build();

        personService.updatePerson(1, dto);

        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void shouldUpdateExistingPersonAddressTypeWhenPersonExistsAndAddressTypeIsDifferent() {
        lenient().when(zipCodeValidatorService.validator(anyString())).thenReturn(true);

        Person existingPerson = Person.builder()
                .id(1)
                .completeName("person test")
                .birthday("bd test")
                .addresses(Collections.singletonList(
                        Address.builder()
                                .id(1)
                                .publicPlace("public place test")
                                .zipCode("zc test")
                                .number("number test")
                                .city("city test")
                                .state("state test")
                                .addressType(AddressType.MAIN)
                                .build()
                ))
                .build();
        when(personRepository.findByCompleteName(anyString())).thenReturn(Optional.of(existingPerson));

        when(addressRepository.findByZipCode(anyString())).thenReturn(Optional.empty());

        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("person test")
                .birthDay("new bd test")
                .publicPlace("new public place test")
                .zipCode("new zc test")
                .number("new number test")
                .city("new city test")
                .state("new state test")
                .addressType(AddressType.MAIN)
                .build();


        Person updatedPerson = personService.cratePerson(dto);


        assertEquals(existingPerson, updatedPerson);


        Address mainAddress = updatedPerson.getAddresses().stream()
                .filter(address -> address.getAddressType() == AddressType.DEFAULT)
                .findFirst().orElse(null);
        assertNotNull(mainAddress);
        assertEquals(AddressType.DEFAULT, mainAddress.getAddressType());

        verify(addressRepository, times(1)).save(any(Address.class));
    }


    @Test
    void shouldNotUpdatePersonBecausePersonNotFound(){
        Address address = Address.builder()
                .id(1)
                .publicPlace("place test")
                .zipCode("zip code")
                .number("number test")
                .city("city test")
                .state("state test")
                .addressType(AddressType.MAIN)
                .build();

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Person person = Person.builder()
                .id(1)
                .completeName("person test")
                .birthday("birthday test")
                .addresses(addresses)
                .build();
        personRepository.save(person);
        verify(personRepository,times(1)).save(person);
        PersonUpdateDto dto = PersonUpdateDto.builder()
                .completeName("person test")
                .birthDay("birthday test")
                .build();

        assertThrows(PersonNotFoundException.class, () -> {
            personService.updatePerson(2, dto);
        });


    }
    @Test
    void shouldCreatePersonWithExistingAddress() {
        Address existingAddress = Address.builder()
                .id(1)
                .publicPlace("existing place")
                .zipCode("existing zip code")
                .number("existing number")
                .city("existing city")
                .state("existing state")
                .addressType(AddressType.MAIN)
                .build();

        when(addressRepository.findByZipCode("existing zip code")).thenReturn(Optional.of(existingAddress));

        when(personRepository.findByCompleteName("new person")).thenReturn(Optional.empty());

        PersonRequestDto dto = PersonRequestDto.builder()
                .completeName("new person")
                .birthDay("birthday test")
                .publicPlace("existing place")
                .zipCode("existing zip code")
                .number("new number")
                .city("existing city")
                .state("existing state")
                .addressType(AddressType.MAIN)
                .build();

        personService.cratePerson(dto);

        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testAddressTypeEnums() {
        AddressType defaultType = mock(AddressType.class);
        AddressType mainType = mock(AddressType.class);

        when(defaultType.getMainAddress()).thenReturn("DEFAULT");
        when(mainType.getMainAddress()).thenReturn("MAIN");

        assertEquals("DEFAULT", defaultType.getMainAddress());
        assertEquals("MAIN", mainType.getMainAddress());
    }

}
