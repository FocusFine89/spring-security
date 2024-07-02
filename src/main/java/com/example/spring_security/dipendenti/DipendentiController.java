package com.example.spring_security.dipendenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    @Autowired
    private DipendentiService dipendentiService;

//    //Creazione Dipendenti
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Dipendenti saveDipendente(@RequestBody @Validated DipendentiDTO dipendente){
//        return dipendentiService.saveDipendente(dipendente);
//    }

    //Modifica Dipendenti

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendenti updateDipendente(@RequestBody @Validated DipendentiDTO dipendente, @PathVariable long id){
        return dipendentiService.findByIdAndUpdate(id, dipendente);
    }

    //Lista di tutti i Dipendenti
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendenti> findAllDipendenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return dipendentiService.findAllDipendenti(page, size);
    }

    //Cerca Dipendente per ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendenti findById(@PathVariable long id){
        return dipendentiService.findById(id);
    }

    //Elimina Dipendente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteDipendente(@PathVariable long id){
        dipendentiService.findByIdAndDelete(id);
    }

    //Cambiare immagine di profilo del Dipendente
//    @PostMapping("/{userId}/avatar")
//    public String  uploadAvatar(@PathVariable int userId, @RequestParam("avatar")MultipartFile image) throws IOException {
//        return this.dipendentiService.uploadImage(image, userId);
//    }

}
