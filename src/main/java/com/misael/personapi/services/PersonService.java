package com.misael.personapi.services;

import com.misael.personapi.entities.Address;
import com.misael.personapi.entities.Person;
import com.misael.personapi.entities.dtos.PersonRequestDto;
import com.misael.personapi.entities.dtos.PersonUpdateDto;
import com.misael.personapi.entities.dtos.PersonWithAddressListDto;
import com.misael.personapi.entities.enums.AddressType;
import com.misael.personapi.exceptions.PersonNotFoundException;
import com.misael.personapi.repositories.AddressRepository;
import com.misael.personapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ZipCodeValidatorService zipCodeValidatorService;

    public List<PersonWithAddressListDto> listAllPersonsWithAddress(){
        List<Person> allPersons = personRepository.findAll();
        List<PersonWithAddressListDto> persons = new ArrayList<>();

        for (Person person : allPersons) {
            List<Address> personAddresses = person.getAddresses();

            PersonWithAddressListDto dto = PersonWithAddressListDto.builder()
                    .id(person.getId())
                    .completeName(person.getCompleteName().toUpperCase())
                    .birthDay(person.getBirthday().toUpperCase())
                    .addresses(personAddresses)
                    .build();

            persons.add(dto);
        }

        return persons;
    }


    public PersonWithAddressListDto findPersonById(Integer id) {
        Optional<Person> findId = personRepository.findById(id);
        if (findId.isPresent()) {
            Person person = findId.get();
            List<Address> addresses = person.getAddresses();

            return PersonWithAddressListDto.builder()
                    .id(person.getId())
                    .completeName(person.getCompleteName())
                    .birthDay(person.getBirthday())
                    .addresses(addresses)
                    .build();
        } else {
            throw new PersonNotFoundException();
        }
    }




    public Person cratePerson(PersonRequestDto dto){
        Optional<Person> findPerson = personRepository.findByCompleteName(dto.completeName());
        Optional<Address> findAddressByZipCode = addressRepository.findByZipCode(dto.zipCode());
        if(findPerson.isPresent()){
            Person person = findPerson.get();
            for (Address address: person.getAddresses()){
                if(address.getAddressType() == AddressType.MAIN && dto.addressType() == AddressType.MAIN &&
                        zipCodeValidatorService.validator(dto.zipCode())){
                    address.setAddressType(AddressType.DEFAULT);

                    Address newAddressMain = Address.builder()
                            .publicPlace(dto.publicPlace())
                            .zipCode(dto.zipCode())
                            .number(dto.number())
                            .city(dto.city())
                            .state(dto.state())
                            .addressType(dto.addressType())
                            .build();

                    List<Address>newAddressMainOnList = new ArrayList<>();
                    newAddressMainOnList.add(newAddressMain);
                    newAddressMainOnList.add(address);

                    person.setAddresses(newAddressMainOnList);
                    addressRepository.save(newAddressMain);
                    personRepository.save(person);
                }
            }
            return person;
        }else{
            if(findAddressByZipCode.isPresent()){
                Address address = findAddressByZipCode.get();
                Address address1 = Address.builder()
                        .publicPlace(address.getPublicPlace())
                        .zipCode(address.getZipCode())
                        .number(dto.number())
                        .city(address.getCity())
                        .state(address.getState())
                        .addressType(dto.addressType())
                        .build();
                addressRepository.save(address1);
                List<Address> addressList = new ArrayList<>();
                addressList.add(address1);
                Person person = Person.builder()
                        .completeName(dto.completeName())
                        .birthday(dto.birthDay())
                        .addresses(addressList)
                        .build();
                personRepository.save(person);
                return person;
            }else{
                Address address1 = Address.builder()
                        .publicPlace(dto.publicPlace())
                        .zipCode(dto.zipCode())
                        .number(dto.number())
                        .city(dto.city())
                        .state(dto.state())
                        .addressType(dto.addressType())
                        .build();
                addressRepository.save(address1);
                List<Address> addressList = new ArrayList<>();
                addressList.add(address1);
                Person person = Person.builder()
                        .completeName(dto.completeName())
                        .birthday(dto.birthDay())
                        .addresses(addressList)
                        .build();
                personRepository.save(person);
                return person;
            }

        }
    }
    public PersonUpdateDto updatePerson(Integer id, PersonUpdateDto dto){
        Optional<Person> findPerson = personRepository.findById(id);
        if(findPerson.isPresent()){
            Person person = findPerson.get();
            person.setCompleteName(dto.completeName());
            person.setBirthday(dto.birthDay());
            personRepository.save(person);
            return PersonUpdateDto.builder()
                    .completeName(person.getCompleteName())
                    .birthDay(person.getBirthday())
                    .build();
        }else{
            throw new PersonNotFoundException();
        }
    }


}
