package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.Address;
import lombok.Builder;

import java.util.List;
@Builder
public record PersonWithAddressListDto(
        Integer id,
        String completeName,
        String birthDay,
        List<Address> addresses
) {
}
