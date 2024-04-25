package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.Address;
import com.misael.personapi.entities.enums.AddressType;
import lombok.Builder;

@Builder
public record PersonRequestDto(
        String completeName,
        String birthDay,
        String publicPlace,
        String zipCode,
        String number,
        String city,
        String state,
        AddressType addressType

){

}
