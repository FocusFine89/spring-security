package com.example.spring_security.security;

import com.example.spring_security.dipendenti.Dipendenti;
import com.example.spring_security.exceptions.UnauthorizedException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Dipendenti dipendente){ // Dato un Dipendente mi genera un Token
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione del Token (VA MESSA IN MILLISECONDI) - IAT
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 *60 *24 *7 /* così il token dure 1 settimana*/)) //Data di scadenza del Token (VA MESSA IN MILLISECONDI)
                .subject(String.valueOf(dipendente.getId())) //Qui va il Subject, ovvero a chi appartiene il Token (NON VANNO MESSI DATI SENSIBILI QUI)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Qui firmo il Token passandogli il segreto
                .compact();
    }

    public void verifyToken(String token){ //Passandogli un Token, verifica se è valido
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
            //Il metodo parse(token) lancerà varie eccezzioni se il token sarà scaduto, maolformato o manipolato
        }catch(Exception ex){
            throw new UnauthorizedException("Problemi con il Token, Effettua di nuovo il login");
            // Non importa se l'eccezione lanciata da .parse()
            // sia un'eccezione perché il token è scaduto o malformato o manipolato, a noi interessa solo che il risultato sia un 401
        }

    }

    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject(); //Il Subjecy è direttamente l'id dell'utente
    }


}
