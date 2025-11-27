package com.jonathan.futebol_api.adapter.dto;


public record TabelaCampeonatoDTO(
        Long idClube,
        String nomeClube,
        Integer pontos,
        Integer jogos,
        Integer vitorias,
        Integer empates,
        Integer derrotas,
        Integer golsMarcados,
        Integer golsSofridos,
        Integer saldoGols


) {
}
