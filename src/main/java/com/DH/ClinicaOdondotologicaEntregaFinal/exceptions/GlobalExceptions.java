package com.DH.ClinicaOdondotologicaEntregaFinal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> procesarBadRequestException(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> procesarNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
