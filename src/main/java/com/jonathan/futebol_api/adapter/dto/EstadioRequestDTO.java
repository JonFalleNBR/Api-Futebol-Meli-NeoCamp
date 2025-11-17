package com.jonathan.futebol_api.adapter.dto;

import jakarta.validation.constraints.NotBlank;

public record EstadioRequestDTO(  @NotBlank  String nome ) {}
