package com.example.spring_security.dipendenti;

import com.example.spring_security.exceptions.BadRequestException;
import com.example.spring_security.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendentiService {

    @Autowired
    DipendentiRepository dipendentiRepository;

//    @Autowired
//    private Cloudinary cloudinaryUploader;

    //Creazione Dipendente
    public Dipendenti saveDipendente(DipendentiDTO newDipendente){
        //1.Verificare se il dipendente già esiste nel database (lo facciamo tramite E-mail)
        this.dipendentiRepository.findByEmail(newDipendente.email()).ifPresent(
                dipendenti -> {
                    throw new BadRequestException("Un dipendente con l'email " + newDipendente.email() + " già esiste");
                }
        );

        //Ora creiamo un nuovo oggetto Dipendente generando per ora automaticamente l'url dell'immagine profilo
        String url = "https://ui-avatar.com/api/?name" + newDipendente.name() + "+" + newDipendente.surname();
        Dipendenti dipendente = new Dipendenti(newDipendente.name(), newDipendente.surname(), newDipendente.email());
        dipendente.setProfileImageUrl(url);
        return dipendentiRepository.save(dipendente);
    }

    //Modifica Dipendente
    public Dipendenti findByIdAndUpdate(long id, DipendentiDTO updatedDipendente){
        //Cerchiamo un dipendente pr ID
        Dipendenti found = this.findById(id);
        found.setName(updatedDipendente.name());
        found.setSurname(updatedDipendente.surname());
        found.setEmail(updatedDipendente.email());
        found.setProfileImageUrl("https://ui-avatar.com/api/?name=" + updatedDipendente.name() + "+" + updatedDipendente.surname());
        return dipendentiRepository.save(found);

    }

    //Lista di tutti i Dipendenti
    public Page<Dipendenti> findAllDipendenti(int pageNumber, int pageSize){
        if(pageSize>100) pageSize=100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.dipendentiRepository.findAll(pageable);
    }


    //Cerca Dipendente per ID
    public Dipendenti findById(long id){
        return dipendentiRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    //Elimina dipendente
    public void findByIdAndDelete(long id){
        Dipendenti found = this.findById(id);
        this.dipendentiRepository.delete(found);
    }


    //---------DA FARE PER ULTIMO-----------

    //settare immagine di profilo da cloudinary
//    public String uploadImage(MultipartFile file, long id) throws IOException{
//        //cerco un dipendente con l'id passato
//        Dipendenti foundDipendente = this.findById(id);
//        String url= (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
//        foundDipendente.setProfileImageUrl(url);
//        return url;
//    }



}
