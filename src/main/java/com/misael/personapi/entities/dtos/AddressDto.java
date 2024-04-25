package com.misael.personapi.entities.dtos;

import com.misael.personapi.entities.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressDto(

        Integer addressId,
        @NotNull(message = "Campo publicPlace(LOGRADOURO) não pode ser nulo")
        @NotBlank(message = "Campo publicPlace não pode ser vazio")
        String publicPlace,
        @NotNull(message = "Campo zipCode(CEP) não pode ser nulo")
        @NotBlank(message = "Campo zipCode(CEP) não pode ser vazio")
        String zipCode,
        @NotNull(message = "Campo number não pode ser nulo")
        @NotBlank(message = "Campo number não pode ser vazio")
        String number,
        @NotNull(message = "Campo city não pode ser nulo")
        @NotBlank(message = "Campo city não pode ser vazio")
        String city,
        @NotNull(message = "Campo state não pode ser nulo")
        @NotBlank(message = "Campo state não pode ser vazio")
        String state,
        AddressType addressType
) {
}
