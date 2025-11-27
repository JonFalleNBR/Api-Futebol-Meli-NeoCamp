package com.jonathan.futebol_api.adapter.dto;

import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;

import java.time.LocalDateTime;

public record PartidaResponseDTO(

        Long Id,
        Long idClubeMandante,
        String nomeClubeMandante,
        Integer golsClubeMandante,

        Long idClubeVisitante,
        String nomeClubeVisitante,
        Integer golsClubeVisitante,

        String nomeEstadio,
        LocalDateTime dataHoraPartida,
        String dataHoraFormatada,

        String resultado // ex 2 x 1

) { }


