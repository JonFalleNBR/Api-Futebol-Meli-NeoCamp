package com.jonathan.futebol_api.adapter.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.awt.*;

public record ClubeRequestDto(
        @NotBlank String nome,
        @NotNull Integer idEstadio,
        @NotNull String estado
) {}

