package com.misael.personapi.entities.dtos;

import lombok.Builder;

@Builder
public record PersonUpdateDto(
        String completeName,
        String birthDay
) {
}
