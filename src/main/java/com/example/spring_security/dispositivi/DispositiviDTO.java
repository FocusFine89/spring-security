package com.example.spring_security.dispositivi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DispositiviDTO(
        @NotEmpty(message = "Non puoi lasciare la tipologia vuota")
        @Size(min = 3, max = 20, message = "La tipologia deve essere compresa tra i 3 e i 20 caratteri")
        String tipologia,
        @NotEmpty(message = "Non puoi lasciare lo stato vuoto")
        @Size(min = 3, max =20, message = "Lo stato deve essere compreso tra 3 3 e i 20 caratteri")
        String stato,
        long dipendenteId) {
}
