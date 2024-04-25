package com.misael.personapi.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Test
    void testPersonNotFoundExceptionHandling() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<String> responseEntity = handler.userNotFound();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        assertEquals("Pessoa não encontrada", responseEntity.getBody());
    }
}
