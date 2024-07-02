package com.example.spring_security.security;

import com.example.spring_security.dipendenti.Dipendenti;
import com.example.spring_security.dipendenti.DipendentiService;
import com.example.spring_security.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.rmi.server.ServerCloneException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter { //<--- IMPORTANTE, estendere la classe con OncePerRequestFilter

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    DipendentiService dipendentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Il codice qui sotto verrà eseguito per ogni richiesta effettuata da un client

        //1.Controlliamo se nella richiesta ci sia un Authorization Header, se non c'è lanciamo un errore 401
        String authHeader = request.getHeader("Authorization"); //Sarà formato da: "Authorization": "Bearer {Token}"
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Token non valido, assicurati di averlo scritto bene!");

        //2.Se c'è il Token lo estraiamo dall'Header
        String accessToken = authHeader.substring(7); //Qui saltiamo tutta la parte del "Bearer " e ci prendiamo solo il Token

        //3.Verifichiamo se il Token è valido o se è stato manipolato in qualche modo (Verifica della Signature)
        //prendiamo il metodo che abbiamo creato nel file JWTTools
        jwtTools.verifyToken(accessToken);


        //Se vogliamo abilitare l'autorizzazione delle richieste dobbiamo informare spring security di chi sia la richiesta (User, Admin o altro..)

        //4.1 Cerchiamo l'utente tramite id (l'id lo troviamo nel payload del Token creato quando un utente si registra o fa il login)
        String dipendenteId = jwtTools.extractIdFromToken(accessToken);
        Dipendenti currentDipendente = dipendentiService.findById(Long.parseLong(dipendenteId));

        //4.2 una volta Trovato il Dipendente posso associarlo al Security Context,
        // praticamente questo equivale ad 'associare' l'utente autenticato alla richiesta corrente
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentDipendente, null, currentDipendente.getAuthorities());
        // Il terzo parametro è OBBLIGATORIO se si vuol poter usare i vari @PreAuthorize perché esso contiene la lista dei ruoli dell'utente
        SecurityContextHolder.getContext().setAuthentication(authentication);



        //4.3 Se è tutto okay, proseguiamo con i prossimi controlli
        filterChain.doFilter(request, response); //Vado al prossimo elemento della catena, passandogli la richiesta corrente e l'oggetto response
    }

    //Facendo l'ovveride di questo metodo posso lasciare alcuni end point senza bispgno di autenticazione
    //In questo caso ci interessa avere gli end point del login e della registrazione aprti senza bisogno del token
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Si usa questo Metodo per specificare in quali situazioni non bisogna usare i Filtri creati sopra
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
        //Così escludiamo ogni end point dentro il controller /auth
    }

}
