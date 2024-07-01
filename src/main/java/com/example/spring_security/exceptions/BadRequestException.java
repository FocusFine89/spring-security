package com.example.spring_security.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BadRequestException extends RuntimeException{
    //Attributi
    private List<ObjectError> errorList;

    //Costruttori
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(List<ObjectError> errorList){
        super("Ci sono stati errori nella Validazione dei Payload");
        this.errorList=errorList;
    }


    //Metodi
    public List<ObjectError> getErrorList() {
        return errorList;
    }

}
