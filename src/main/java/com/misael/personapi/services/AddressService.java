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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;


    public AddressDto crateNewAddress(Integer id, AddressRequestDto dto){
        Optional<Person> findPerson = personRepository.findById(id);
        Optional<Address> findAddress = addressRepository.findByNumber(dto.number());
        if(findPerson.isPresent() && findAddress.isEmpty()){
            Person person = findPerson.get();
           AddressType addressType = dto.addressType();

           List<Address> addresses = person.getAddresses();
           if(addresses == null){
               addresses = new ArrayList<>();
               person.setAddresses(addresses);
           }

           if(addressType == AddressType.MAIN){
               person.getAddresses().forEach(address -> address.setAddressType(AddressType.DEFAULT));
           }
            Address newAddress = Address.builder()
                    .publicPlace(dto.publicPlace())
                    .zipCode(dto.zipCode())
                    .number(dto.number())
                    .city(dto.city())
                    .state(dto.state())
                    .addressType(addressType)
                    .build();

            addressRepository.save(newAddress);
            person.getAddresses().add(newAddress);
            personRepository.save(person);

            return AddressDto.builder()
                    .addressId(newAddress.getId())
                    .publicPlace(newAddress.getPublicPlace())
                    .zipCode(newAddress.getZipCode())
                    .number(newAddress.getNumber())
                    .city(newAddress.getCity())
                    .state(newAddress.getState())
                    .addressType(newAddress.getAddressType())
                    .build();
        } else {
            throw new PersonNotFoundException();
        }
    }

    public List<AddressDto> listAllAddress(){
        List<Address> addressList= addressRepository.findAll();
        List<AddressDto> dtos = new ArrayList<>();
        for(Address address:addressList){
            AddressDto dto = AddressDto.builder()
                    .addressId(address.getId())
                    .publicPlace(address.getPublicPlace())
                    .zipCode(address.getZipCode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .state(address.getState())
                    .addressType(address.getAddressType())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }


    public AddressDto updateAddress(Integer personId, Integer addressId, AddressUpdateDto dto) {
        Optional<Person> findPerson = personRepository.findById(personId);

        if (findPerson.isPresent()) {
            Person person = findPerson.get();
            List<Address> addresses = person.getAddresses();

            for (Address address : addresses) {
                if (address.getId().equals(addressId)) {

                    address.setPublicPlace(dto.publicPlace());
                    address.setZipCode(dto.zipCode());
                    address.setNumber(dto.number());
                    address.setCity(dto.city());
                    address.setState(dto.state());
                    address.setAddressType(dto.addressType());

                    if (dto.addressType() == AddressType.MAIN) {
                        for (Address addr : addresses) {
                            if (addr.getAddressType() == AddressType.MAIN) {
                                addr.setAddressType(AddressType.DEFAULT);
                                break;
                            }
                        }
                        address.setAddressType(AddressType.MAIN);
                    }

                    personRepository.save(person);


                    return mapToAddressDto(address);
                }
            }
        }

        return null;
    }

    public AddressDto mapToAddressDto(Address address) {
        return AddressDto.builder()
                .addressId(address.getId())
                .publicPlace(address.getPublicPlace())
                .zipCode(address.getZipCode())
                .number(address.getNumber())
                .city(address.getCity())
                .state(address.getState())
                .addressType(address.getAddressType())
                .build();
    }


}
