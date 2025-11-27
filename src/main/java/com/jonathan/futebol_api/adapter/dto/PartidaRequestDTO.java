package com.jonathan.futebol_api.adapter.dto;

import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PartidaRequestDTO(


       @NotNull Long idClubeMandante,
        @NotNull Long idClubeVisitante,
         @NotNull Long idEstadio,
        @NotNull LocalDateTime horarioPartida, // TODO logica par que data seja formatada corretamente

        @NotNull Integer golsMandante,

        @NotNull Integer golsVisitante



) {
}