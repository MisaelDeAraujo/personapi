package com.misael.personapi.controllers;

import com.misael.personapi.entities.Person;
import com.misael.personapi.entities.dtos.PersonRequestDto;
import com.misael.personapi.entities.dtos.PersonUpdateDto;
import com.misael.personapi.entities.dtos.PersonWithAddressListDto;
import com.misael.personapi.services.PersonService;
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
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Operation(summary = "Realiza cadastro de Pessoas e Endereço, caso o Endereço já tenha sido cadastrado com outra pessoa," +
            "a API irá adicionar o Endereço automaticamente pelo CEP. Caso realize um novo cadastro de endereço com o mesmo" +
            "nome de Pessoa, será incluido automaticamente na lista de endereços dessa Pessoa, e se o novo endereço dessa Pessoa" +
            "vai ser o novo MAIN, caso ela já possua um endereço MAIN, vai se tornar DEFAULT e o novo endereço MAIN será incluído.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos." +
                    "ou se Usuário está cadastrado."),
    })
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonRequestDto dto){
        Person dto2 = personService.cratePerson(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto2);
    }

    @Operation(summary = "Realiza listagem de todos as Pessoas, listando Pessoa e Endereços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem feita com sucesso."),
    })
    @GetMapping
    public ResponseEntity<List<PersonWithAddressListDto>> listAllPersons(){
        return ResponseEntity.status(HttpStatus.OK).body(personService.listAllPersonsWithAddress());
    }

    @Operation(summary = "Realiza listagem de pessoa por ID, listando uma única pessoa com todos os endereços cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "400", description = "Pessoa não encontrada")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonWithAddressListDto> findById(@PathVariable(value = "id") Integer id){
        PersonWithAddressListDto dto = personService.findPersonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(summary = "Altera valor de uma Pessoa encontrada por ID, digite o ID da pessoa e depois" +
            "os valores que deseja alterar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "400", description = "Pessoa não encontrada")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonUpdateDto> updatePerson(@PathVariable(value = "id")Integer id,@RequestBody @Valid PersonUpdateDto dto){
        PersonUpdateDto dto2 = personService.updatePerson(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto2);
    }


}
