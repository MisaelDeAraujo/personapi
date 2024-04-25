package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.enums.AddressType;
import lombok.Builder;

@Builder
public record PersonDto(
        Integer id,
        String completeName,
        String birthDay,
        String publicPlace,
        String zipCode,
        String number,
        String city,
        String state,
        AddressType addressType
) {
}
