package com.example.spring_security.dipendenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendentiDTO(
        @NotEmpty(message = "Non puoi lasciare il nome vuoto")
        @Size(min = 3, max = 15, message = "il nome deve essere compreso tra i 3 e i 15 caratteri")
        String name,
        @NotEmpty(message = "Non puoi lasciare il cognome vuoto")
        @Size(min = 3, max = 15, message = "il cognome deve essere compreso tra i 3 e i 15 caratteri")
        String surname,
        @NotEmpty(message = "Non puoi lasciare l'email vuota")
        @Email(message = "L'email inserita non è valida")
        String email) {
}
