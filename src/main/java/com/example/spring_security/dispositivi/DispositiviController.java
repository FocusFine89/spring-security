package com.example.spring_security.dispositivi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispositivi")
public class DispositiviController {
    @Autowired
    private DispositiviService dispositiviService;

    //Creazione Dispositivi
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivi saveDispositivo(@RequestBody @Validated DispositiviDTO dispositivo){
        return this.dispositiviService.saveDispositivo(dispositivo);
    }

    //Modifica Dispositivi
    @PutMapping("/{id}")
    public Dispositivi updateDispositivo(@RequestBody @Validated DispositiviDTO newDispositivo, @PathVariable long id){
        return this.dispositiviService.findByIdAndUpdate(id, newDispositivo);
    }

    //Lista di tutti i Dispositivi
    @GetMapping
    public Page<Dispositivi> findAllDispositivi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return this.dispositiviService.findAllDispositivi(page, size);
    }

    //Dispositivo per ID
    @GetMapping("/{id}")
    public Dispositivi findById(@PathVariable long id){
        return this.dispositiviService.findById(id);
    }

    //Elimina Dispositivo
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id){
        this.dispositiviService.deleteById(id);
    }
}
