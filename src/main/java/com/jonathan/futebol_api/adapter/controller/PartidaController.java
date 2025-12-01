package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.mapper.PartidaMapper;
import com.jonathan.futebol_api.core.usercase.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;


    @PostMapping
    public ResponseEntity<PartidaResponseDTO> criarPartida(@RequestBody @Valid PartidaRequestDTO partidaRequestDTO) {
      PartidaResponseDTO novaPartida = partidaService.cadastrarNovaPartida(partidaRequestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(novaPartida);
    }


    @GetMapping
    public ResponseEntity<Page<PartidaResponseDTO>> listarPartidas(Pageable pageable) {
        Page<Partida> partidas = partidaService.listaTodasPartidas(pageable);
        Page<PartidaResponseDTO> responseDTOs = partidas.map(PartidaMapper.INSTANCE::toResponseDTO);

        return ResponseEntity.ok(responseDTOs);

    }


    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> listaPartidaPorId(@PathVariable Long id) {
            PartidaResponseDTO listaPartida = partidaService.listaPartidaPorId(id);
            return ResponseEntity.ok(listaPartida);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPartida(@PathVariable Long id){
         partidaService.deletarPartidaHistorico(id);
         return ResponseEntity.noContent().build();

    }


    /// -----  Buscas Personalizadas de partidas e estatisticas
    @GetMapping("por-clube/{idClube}")
    private ResponseEntity<Page<PartidaResponseDTO>> listaPartidaPorClube(@PathVariable Long idClube, Pageable pageable){

        Page<PartidaResponseDTO> partidas = partidaService.listarPartidaPorClube(idClube, pageable);
        return ResponseEntity.ok(partidas);
        
        // TODO AJUSTAR requisicao de busca geral da partida pro clube para que traga se resultado da partida foi vitoria ou derrota para o time cujo id esta sendo buscado

        // TODO concluir as demais buscas avancadas
    }


}
