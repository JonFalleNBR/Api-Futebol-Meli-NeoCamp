package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.adapter.dto.EstadioRequestDTO;
import com.jonathan.futebol_api.adapter.dto.EstadioResponseDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

    @Autowired
    public EstadioService service;
    @Autowired
    public EstadioRepository estadioRepository;



    @PostMapping
    public ResponseEntity<EstadioResponseDTO> cadastrarEstadio(@RequestBody EstadioRequestDTO requestDTO){
        Estadio estadio = new Estadio();

        estadio.setNome(requestDTO.nome());


        Estadio savedEstadio = service.cadastrarEstadio(estadio);

        EstadioResponseDTO clubeResponseDTO = new EstadioResponseDTO(
                savedEstadio.getNome()


        );

        return ResponseEntity.status(HttpStatus.CREATED).body(clubeResponseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioResponseDTO> editarInfoEstadio( @PathVariable Integer id  , @RequestBody EstadioRequestDTO estadioRequestDTO){


        Estadio estadioExistente = service.buscarEstadioPorId(id)
                .orElseThrow(() -> new RuntimeException(utils.MensagensException.ESTADIO_INEXISTENTE.getMensagem())); // Abordagem mais simples para o caso da busca por id caso o Estadio nao exista

        estadioExistente.setNome(estadioRequestDTO.nome());

        Estadio estadioUpdated = service.editarEstadio(id, estadioExistente);

        EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(
                estadioUpdated.getNome()

        );

        return ResponseEntity.ok(estadioResponseDTO);


    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioResponseDTO> buscarEstadioPorId(@PathVariable Integer id){
        Optional<Estadio> estadio = service.buscarEstadioPorId(id);

        return estadio.map(estadioExistente -> {
                EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(estadioExistente.getNome());
                return ResponseEntity.ok(estadioResponseDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity <List<EstadioResponseDTO>> getEstadio(@PathVariable Integer id){
        List<Estadio> estadios = service.listaTodosEstadios();

        List<EstadioResponseDTO> estadioResponseDTOs = estadios.stream().map(
                estadio -> new EstadioResponseDTO(estadio.getNome()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(estadioResponseDTOs);

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
