package com.jonathan.futebol_api.adapter.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClubeRequestDto(
        @NotBlank String nome,
        @NotNull Integer idEstadio,
        @NotNull String estado,

        @NotNull Integer vitorias,

        @NotNull Integer empates,

        @NotNull Integer derrotas,

        Integer ativo
) {}

