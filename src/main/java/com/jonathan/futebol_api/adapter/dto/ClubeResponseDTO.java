package com.jonathan.futebol_api.adapter.dto;

import jakarta.validation.constraints.NotBlank;

public record ClubeResponseDTO(
        @NotBlank String nome,
        String estado,
        String estadio,
        Integer vitorias,
        Integer empates,
        Integer derrotas,
        Boolean ativo

) {}
