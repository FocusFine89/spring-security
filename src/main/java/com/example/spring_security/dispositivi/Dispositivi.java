package com.example.spring_security.dispositivi;

import com.example.spring_security.dipendenti.Dipendenti;
import jakarta.persistence.*;


@Entity
@Table(name = "dispositivi")
public  class Dispositivi {
    //Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String tipologia;
    protected String stato;

    @ManyToOne
    @JoinColumn(name = "dipendente", nullable = true)
    protected Dipendenti dipendente;

    //Costruttori
    public Dispositivi(){}

    public Dispositivi(String tipologia, String stato, Dipendenti dipendente) {
        this.tipologia = tipologia;
        this.stato = stato;
        this.dipendente = dipendente;
    }

    public Dispositivi(String tipologia) {
        this.tipologia = tipologia;
    }
    //Metodi


    public long getId() {
        return id;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Dipendenti getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendenti dipendente) {
        this.dipendente = dipendente;
    }

    @Override
    public String toString() {
        return "Dispositivi{" +
                "id=" + id +
                ", tipologia='" + tipologia + '\'' +
                ", stato='" + stato + '\'' +
                ", dipendente=" + dipendente +
                '}';
    }
}
