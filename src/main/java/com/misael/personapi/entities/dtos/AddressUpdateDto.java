package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.enums.AddressType;
import lombok.Builder;

@Builder
public record AddressUpdateDto(

        String publicPlace,

        String zipCode,

        String number,

        String city,

        String state,
        AddressType addressType
) {
}
