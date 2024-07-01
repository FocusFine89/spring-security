package com.example.spring_security.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("Record con id " + id + " non trovato");
    }

    public NotFoundException(String email){
        super("Dipendente con email " + email + " Non trovato");
    }
}
