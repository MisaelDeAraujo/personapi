package com.misael.personapi.controllers;

import com.misael.personapi.entities.dtos.AddressDto;
import com.misael.personapi.entities.dtos.AddressRequestDto;
import com.misael.personapi.entities.dtos.AddressUpdateDto;
import com.misael.personapi.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @Operation(summary = "Realiza cadastro de um novo endereço para uma Pessoa existente, Digite o ID da pessoa e depois" +
            "os dados do novo endereço. Caso esse novo endereço o campo anddressType seja MAIN, se a Pessoa já possui um Endereço MAIN" +
            "será automaticamnte alterado para DEFAULT, e o novo endereço MAIN será colocado na lista de endereço dessa Pessoa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa encontrada, Endereço cadastrado"),
            @ApiResponse(responseCode = "400", description = "Pessoa não encontrada")
    })
    @PostMapping(value = "/{personId}")
    public ResponseEntity<AddressDto> createNewAddress(@RequestParam(value = "personId") Integer personId, @RequestBody @Valid AddressRequestDto dto) {
        AddressDto dto2 = addressService.crateNewAddress(personId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto2);
    }

    @Operation(summary = "Realiza listagem de todos os endereços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem feita com sucesso"),
    })
    @GetMapping
    public ResponseEntity<List<AddressDto>> ListAllAddress() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.listAllAddress());
    }

    @Operation(summary = "Realiza alteração em endereço já cadastrado de uma Pessoa, digite o ID do endereço e depois" +
            "os valores que vão ser alterados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço alterado"),
            @ApiResponse(responseCode = "400", description = "Endereço não encontrado")
    })
    @PutMapping(value = "/{personId}/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@RequestParam(value = "personId")Integer personId,
                                                    @RequestParam(value = "addressId")Integer addressId,
                                                    @RequestBody @Valid AddressUpdateDto dto){
        AddressDto dto2 = addressService.updateAddress(personId,addressId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto2);
    }


}

