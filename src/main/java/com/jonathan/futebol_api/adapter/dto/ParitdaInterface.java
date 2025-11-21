package com.jonathan.futebol_api.adapter.dto;

import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;

import java.time.LocalDateTime;

public record ParitdaInterface(

        Clube clubeMandante,
        Clube clubeVisitante,
        Estadio estadio,
        LocalDateTime horarioPartida


) {
}
