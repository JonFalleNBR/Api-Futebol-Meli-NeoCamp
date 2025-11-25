package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.adapter.dto.EstadioRequestDTO;
import com.jonathan.futebol_api.adapter.dto.EstadioResponseDTO;
import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.core.entity.Estadio;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.core.mapper.EstadioMapper;
import com.jonathan.futebol_api.core.usercase.service.EstadioService;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<EstadioResponseDTO> cadastrarEstadio(@RequestBody EstadioRequestDTO requestDTO){

        Estadio estadio = EstadioMapper.toEntity(requestDTO);

        Estadio savedEstadio = service.cadastrarEstadio(estadio);

        EstadioResponseDTO clubeResponseDTO = EstadioMapper.toDto(savedEstadio);

        return ResponseEntity.status(HttpStatus.CREATED).body(clubeResponseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioResponseDTO> editarInfoEstadio( @PathVariable Long id  , @RequestBody EstadioRequestDTO estadioRequestDTO){

        Estadio estadioExistente = service.buscarEstadioPorId(id)
                .orElseThrow(() -> new Exceptions.EstadioInexistenteException(utils.MensagensException.ESTADIO_INEXISTENTE)); // Abordagem mais simples para o caso da busca por id caso o Estadio nao exista

        estadioExistente.setNome(estadioRequestDTO.nome());

        Estadio estadioUpdated = service.editarEstadio(id, estadioExistente);

        EstadioResponseDTO estadioResponseDTO = EstadioMapper.toDto(estadioUpdated);


        return ResponseEntity.ok(estadioResponseDTO);


    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioResponseDTO> buscarEstadioPorId(@PathVariable Long id){
        Optional<Estadio> estadioopt = service.buscarEstadioPorId(id);

        return estadioopt.map(estadio -> {
            EstadioResponseDTO estadioResponseDTO = EstadioMapper.toDto(estadio);
            return ResponseEntity.ok(estadioResponseDTO);

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity <List<EstadioResponseDTO>> listarEstadios(){
        List<Estadio> estadios = service.listaTodosEstadios();

        List<EstadioResponseDTO>  estadiosDtos = EstadioMapper.estadioResponseDTOList(estadios);

        return ResponseEntity.ok(estadiosDtos);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstadio(@PathVariable Long id){
        try{
            service.removerEstadio(id);
            return ResponseEntity.noContent().build();

        }catch (Exceptions.EstadioInexistenteException e){
            return ResponseEntity.notFound().build();

        }
    }


}
