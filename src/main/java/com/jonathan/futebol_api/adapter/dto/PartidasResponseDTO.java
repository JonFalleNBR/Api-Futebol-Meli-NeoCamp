package com.jonathan.futebol_api.adapter.dto;

import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public record PartidasResponseDTO(

        Long Id,
        Clube clubeMandante,
        Clube clubeVisitante,
        Estadio estadioPartida,
        LocalDateTime dataHoraPartida

) { }
