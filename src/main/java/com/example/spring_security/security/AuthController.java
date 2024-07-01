package com.example.spring_security.security;

import com.example.spring_security.dipendenti.Dipendenti;
import com.example.spring_security.dipendenti.DipendentiDTO;
import com.example.spring_security.dipendenti.DipendentiLoginResponseDTO;
import com.example.spring_security.dipendenti.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DipendentiService dipendentiService;

    //Login
    @PostMapping("/login")
    public DipendentiLoginResponseDTO login(@RequestBody DipendentiDTO payload){
        return new DipendentiLoginResponseDTO(authService.loginAndCreateToken(payload));
    }

    //Registrazione
    //Creazione Dipendenti
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendenti saveDipendente(@RequestBody @Validated DipendentiDTO dipendente){
        return dipendentiService.saveDipendente(dipendente);
    }


}
