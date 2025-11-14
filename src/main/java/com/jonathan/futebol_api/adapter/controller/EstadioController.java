package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.core.entity.Estadio;
import com.jonathan.futebol_api.core.usercase.service.EstadioService;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

    @Autowired
    public EstadioService service;
    @Autowired
    public EstadioRepository estadioRepository;



    @PostMapping
    public ResponseEntity<Estadio> cadastrarEstadio(@RequestBody Estadio estadio){
        Estadio savedEstadio = service.cadastrarEstadio(estadio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEstadio);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadio> editarInfoEstadio( @PathVariable Integer id  , @RequestBody Estadio estadio){
        Optional<Estadio>optionalEstadio = service.buscarEstadioPorId(id);
        return optionalEstadio.map(existing -> {
                        existing.setNome(estadio.getNome());
                        Estadio updatedEstadio = estadioRepository.save(existing);
                    return ResponseEntity.ok(updatedEstadio);
                })
                .orElseThrow(() -> new RuntimeException(utils.MensagensException.ESTADIO_INEXISTENTE.getMensagem()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadio> buscarEstadioPorId(@PathVariable Integer id){
        Optional<Estadio> estadio = service.buscarEstadioPorId(id);
        return estadio.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());


    }


    @GetMapping
    public ResponseEntity <List<Estadio>> getEstadio(@PathVariable Integer id){
        List<Estadio> estadios = service.listaTodosEstadios();
        return ResponseEntity.ok(estadios);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstadio(@PathVariable Integer id){
        if(!estadioRepository.existsById(id)){
            return ResponseEntity.notFound().build();

        }
        service.removerEstadio(id);
        return ResponseEntity.noContent().build();

    }




}
