package com.misael.personapi.handlers;

import com.misael.personapi.exceptions.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumenProblemDetail(MethodArgumentNotValidException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList().toString());
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> userNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
    }
}
