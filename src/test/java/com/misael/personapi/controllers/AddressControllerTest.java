package com.misael.personapi.controllers;

import com.misael.personapi.entities.dtos.AddressDto;
import com.misael.personapi.entities.dtos.AddressRequestDto;
import com.misael.personapi.entities.dtos.AddressUpdateDto;
import com.misael.personapi.services.AddressService;
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

import static org.mockito.Mockito.when;
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {
    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Test
    public void testCreateNewAddress() {
        Integer personId = 1;
        AddressRequestDto requestDto = AddressRequestDto.builder().build();
        when(addressService.crateNewAddress(personId, requestDto)).thenReturn(AddressDto.builder().build());

        ResponseEntity<AddressDto> response = addressController.createNewAddress(personId, requestDto);

        assert(response.getStatusCode()).equals(HttpStatus.CREATED);
    }

    @Test
    public void testListAllAddress() {
        List<AddressDto> addresses = new ArrayList<>();
        when(addressService.listAllAddress()).thenReturn(addresses);

        ResponseEntity<List<AddressDto>> response = addressController.ListAllAddress();

        assert(response.getStatusCode()).equals(HttpStatus.OK);
    }

    @Test
    public void testUpdateAddress() {
        Integer personId = 1;
        Integer addressId = 1;
        AddressUpdateDto requestDto = AddressUpdateDto.builder().build();
        when(addressService.updateAddress(personId, addressId, requestDto)).thenReturn(AddressDto.builder().build());

        ResponseEntity<AddressDto> response = addressController.updateAddress(personId, addressId, requestDto);

        assert(response.getStatusCode()).equals(HttpStatus.OK);
    }
}
