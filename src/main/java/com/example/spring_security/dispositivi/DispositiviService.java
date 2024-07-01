package com.example.spring_security.dispositivi;


import com.example.spring_security.dipendenti.Dipendenti;
import com.example.spring_security.dipendenti.DipendentiRepository;
import com.example.spring_security.dipendenti.DipendentiService;
import com.example.spring_security.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DispositiviService {
    @Autowired
    private DispositiviRepository dispositiviRepository;

    @Autowired
    private DipendentiService dipendentiService;

    @Autowired
    private DipendentiRepository dipendentiRepository;

    //Creazione Dispositivi
    public Dispositivi saveDispositivo( DispositiviDTO newDispositivo){
        //cerchiamo l'id di un Dipendente per trovarlo e poterlo associare ad un Dispositivo
        Dipendenti foundDipendente= this.dipendentiRepository.findDipendenteById(newDispositivo.dipendenteId());

        //Creiamo un oggetto Dispositivo per poi salvarlo
        Dispositivi dispositivo = new Dispositivi(newDispositivo.tipologia(), newDispositivo.stato(), foundDipendente);
        return dispositiviRepository.save(dispositivo);
    }

    //modifica dispositivi
    public Dispositivi findByIdAndUpdate(long id, DispositiviDTO updatedDispositivo){
        //Cerchiamo un Dispositivo da modificare per id
        Dispositivi foundDispositivo = this.findById(id);
        //Cerchiamo un Dipendente per id per poterlo settare al Dispositivo
        Dipendenti foundDipendente = this.dipendentiService.findById(updatedDispositivo.dipendenteId());
        //Settiamo tutti i paramentri
        foundDispositivo.setStato(updatedDispositivo.stato());
        foundDispositivo.setTipologia(updatedDispositivo.tipologia());
        foundDispositivo.setDipendente(foundDipendente);

        return this.dispositiviRepository.save(foundDispositivo);

    }


    //Lista di tutti i Dispositivi
    public Page<Dispositivi> findAllDispositivi(int pageNumber, int pageSize){
        if(pageSize>100) pageSize=100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.dispositiviRepository.findAll(pageable);
    }


    //Cerca Dispositivo per ID
    public Dispositivi findById(long id){
        return this.dispositiviRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    //Elimina Dispositivo
    public void deleteById(long id){
        Dispositivi foundDispositivo = this.findById(id);
        this.dispositiviRepository.delete(foundDispositivo);
    }


}
