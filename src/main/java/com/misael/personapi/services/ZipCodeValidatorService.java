package com.misael.personapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ZipCodeValidatorService {

    @Autowired
    private RestTemplate restTemplate;


    public boolean validator(String zipCode){
        boolean notValidated = false;
        ResponseEntity<Map> externalValidator =
                restTemplate.postForEntity("https://run.mocky.io/v3/18f5f56f-f229-4d27-bc8c-4bd5b8d3e8ad",zipCode,Map.class);

        if(externalValidator.getStatusCode() == HttpStatus.OK &&
        externalValidator.getBody().containsValue("Autorizado")){
            return true;
        }else{
            return notValidated;
        }

    }
}
