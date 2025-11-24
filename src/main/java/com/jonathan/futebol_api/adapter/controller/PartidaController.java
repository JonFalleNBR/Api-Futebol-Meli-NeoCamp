package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.core.mapper.PartidaMapper;
import com.jonathan.futebol_api.core.usercase.service.PartidaService;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaRepository partidaRepository;

// TODO CRIAR ENPOINT DE PARTIDA


    @PostMapping
    public   ResponseEntity<PartidaResponseDTO> criarPartida  (@RequestBody PartidaRequestDTO partidaRequestDTO){
            try{
                PartidaResponseDTO novaPartida = partidaService.cadastrarNovaPartida(partidaRequestDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(novaPartida);

            }catch (Exceptions.ClubeInvalidoeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }catch (Exceptions.EstadioInexistenteException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
    }








}
