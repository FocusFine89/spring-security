package com.example.spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class JwtConfig {
    //Per poter configurare la securityFilterChain si deve aggiungere un bean dello stesso tipo (SecurityFilterChain)

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //Ora si possono disabilitare i comportamenti di default che non ci piacciono

        httpSecurity.formLogin(http -> http.disable()); //Disabilita il Form di login che c'è di Default, (Lo faremo nel front-end)
        httpSecurity.csrf(http -> http.disable()); //Disabilita la protezione per gli attacchi CSRF
        httpSecurity.sessionManagement(http-> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //Disabilita le sessioni che con JWT non useremo

        //Si possono anche aggiungere dei filtri Custom

        httpSecurity.authorizeHttpRequests(http-> http.requestMatchers("/**").permitAll()); //Questo evita il 401 per ogni richiesta
        return httpSecurity.build();
    }

}
