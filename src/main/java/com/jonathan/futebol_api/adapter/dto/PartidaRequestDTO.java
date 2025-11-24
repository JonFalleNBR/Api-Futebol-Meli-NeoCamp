package com.jonathan.futebol_api.adapter.dto;

import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;

import java.time.LocalDateTime;

public record PartidaRequestDTO(


        Long idClubeMandante,
        Long idClubeVisitante,
        Long idEstadio,
        LocalDateTime horarioPartida

        // TODO ADICIONAR PLACAR DA PARTIDA


) {
}
