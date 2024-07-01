package com.example.spring_security.dispositivi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispositiviRepository extends JpaRepository<Dispositivi, Long> {
    Optional<Dispositivi> findByStato(String stato);
    Optional<Dispositivi> findByTipologia(String tipologia);
    Optional<Dispositivi>findById(long id);
}
