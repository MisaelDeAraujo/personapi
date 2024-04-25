package com.misael.personapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZipCodeValidatorServiceTest {

    @InjectMocks
    private ZipCodeValidatorService zipCodeValidatorService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testValidatorValidZipCodeReturnsTrue() {
        String validZipCode = "12345678";
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "Autorizado");
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), eq(validZipCode), eq(Map.class))).thenReturn(responseEntity);

        boolean result = zipCodeValidatorService.validator(validZipCode);

        assertTrue(result);
    }
    @Test
    public void testValidatorInvalidZipCodeReturnsFalse() {
        String invalidZipCode = "87654321";
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(restTemplate.postForEntity(anyString(), eq(invalidZipCode), eq(Map.class))).thenReturn(responseEntity);

        boolean result = zipCodeValidatorService.validator(invalidZipCode);

        assertFalse(result);
    }
}
