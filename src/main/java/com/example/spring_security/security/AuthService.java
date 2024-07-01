package com.example.spring_security.security;

import com.example.spring_security.dipendenti.Dipendenti;
import com.example.spring_security.dipendenti.DipendentiDTO;
import com.example.spring_security.dipendenti.DipendentiService;
import com.example.spring_security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;

    @Autowired
    private JWTTools jwtTools;

    public String loginAndCreateToken(DipendentiDTO payload){
        //1. Si controllano le credenziali
        //1.1 Cerco l'utente nel DB (in questo caso tramite email)
        Dipendenti dipendente = this.dipendentiService.findByEmail(payload.email());

        //1.2 Verifico se la Password combacia con quella del payload
        if(dipendente.getName().equals(payload.name())){
            //Se è uguale allora genero il Token per il dipendente
            return jwtTools.createToken(dipendente);
        }else{
            //Altrimenti tiro un errore 401
            throw new UnauthorizedException("Email o Password non corrette, riprova");
        }
    }
}
