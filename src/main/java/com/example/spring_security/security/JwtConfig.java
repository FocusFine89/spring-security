package com.example.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtConfig {
    //Per poter configurare la securityFilterChain si deve aggiungere un bean dello stesso tipo (SecurityFilterChain)

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //Ora si possono disabilitare i comportamenti di default che non ci piacciono

        httpSecurity.formLogin(http -> http.disable()); //Disabilita il Form di login che c'è di Default, (Lo faremo nel front-end)
        httpSecurity.csrf(http -> http.disable()); //Disabilita la protezione per gli attacchi CSRF
        httpSecurity.sessionManagement(http-> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Disabilita le sessioni che con JWT non useremo

        httpSecurity.cors(Customizer.withDefaults()); //NON DIMENTICARE QUESTA PARTE SE SI UTILIZZA UNA CONFIGURAZIONE CORS PERSONALIZZATA

        //Si possono anche aggiungere dei filtri Custom

        httpSecurity.authorizeHttpRequests(http-> http.requestMatchers("/**").permitAll()); //Questo evita il 401 per ogni richiesta
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //così rendiamo per il nostro front-end di accedere al nostro back-end (a questo)
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config); // Registro la configurazione CORS appena fatta a livello globale su tutti gli endpoint del mio server
        return source;
        // Non dimentichiamoci di aggiungere l'impostazione cors anche nella security filter chain qua sopra
    }

    @Bean
    PasswordEncoder getBCrypt(){
        return new BCryptPasswordEncoder(11);
        // 11 è il numero di ROUNDS, ovvero quante volte viene eseguito l'algoritmo BCrypt, ciò ci è utile per determinare quale sarà la velocità di
        // esecuzione di BCrypt. Più è veloce meno sicure saranno le password e ovviamente viceversa. Bisogna comunque sempre tenere in considerazione
        // però anche la UX, quindi se lo rendessimo estremamente lento, la UX peggiorerebbe tantissimo, bisogna quindi trovare il giusto bilanciamento
        // tra sicurezza e UX.
        // 11 ad es significa che l'algoritmo verrà eseguito 2^11 volte, cioè 2048. Su un computer di prestazioni medie ciò significa all'incirca 100/200ms
    }

}
