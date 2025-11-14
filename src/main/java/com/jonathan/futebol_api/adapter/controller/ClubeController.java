package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.usercase.service.ClubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    @Autowired
    private ClubeService service;

    @Autowired
    private ClubeRepository clubeRepository;

    @PostMapping
    public ResponseEntity<Clube> cadastrarClube(@RequestBody Clube clube){
        Clube saveClube = service.cadastrarClube(clube);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClube);


    }


    @GetMapping("/{id}")
    public ResponseEntity<Clube> buscarClube( @PathVariable Integer id){
        Optional<Clube> clube = service.buscarClubePorId(id);
        return clube.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<Clube>> listarClubes(Pageable pageable){
        Page<Clube> clubes = service.listarClubes(pageable);
        return ResponseEntity.ok(clubes);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Clube> editarClube(@PathVariable Integer id,  @RequestBody Clube clube){
        if(!clubeRepository.existsById(id)){
            return  ResponseEntity.notFound().build();

        }

        clube.setIdClube(id);
        Clube updatedClube = service.editarClube(id, clube);
        return ResponseEntity.ok(updatedClube);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativeClube(@PathVariable Integer id){
        if(!clubeRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); //
        }
        service.inativarClube(id);

        return ResponseEntity.noContent().build();

    }

    // TODO -> melhorar os logs de http nas exceptions


}
