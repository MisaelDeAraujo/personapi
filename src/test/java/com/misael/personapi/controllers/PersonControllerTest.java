package com.misael.personapi.controllers;

import com.misael.personapi.entities.Person;
import com.misael.personapi.entities.dtos.PersonRequestDto;
import com.misael.personapi.entities.dtos.PersonUpdateDto;
import com.misael.personapi.entities.dtos.PersonWithAddressListDto;
import com.misael.personapi.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Test
    public void testCreatePerson() {
        PersonRequestDto requestDto = PersonRequestDto.builder().build();
        when(personService.cratePerson(any(PersonRequestDto.class))).thenReturn(Person.builder().build());

        ResponseEntity<Person> response = personController.createPerson(requestDto);

        assert(response.getStatusCode()).equals(HttpStatus.CREATED);
    }

    @Test
    public void testListAllPersons() {
        List<PersonWithAddressListDto> persons = new ArrayList<>();
        when(personService.listAllPersonsWithAddress()).thenReturn(persons);

        ResponseEntity<List<PersonWithAddressListDto>> response = personController.listAllPersons();

        assert(response.getStatusCode()).equals(HttpStatus.OK);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        List<PersonWithAddressListDto> person = new ArrayList<>();
        when(personService.findPersonById(id)).thenReturn(any());

        ResponseEntity<PersonWithAddressListDto> response = personController.findById(id);

        assert(response.getStatusCode()).equals(HttpStatus.OK);
    }

    @Test
    public void testUpdatePerson() {
        Integer id = 1;
        PersonUpdateDto requestDto = PersonUpdateDto.builder().build();
        when(personService.updatePerson(eq(id), any(PersonUpdateDto.class))).thenReturn(PersonUpdateDto.builder().build());


        ResponseEntity<PersonUpdateDto> response = personController.updatePerson(id, requestDto);


        assert(response.getStatusCode()).equals(HttpStatus.OK);
    }
}
