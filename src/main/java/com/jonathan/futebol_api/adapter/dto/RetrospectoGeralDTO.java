package com.jonathan.futebol_api.adapter.dto;

public record RetrospectoGeralDTO (
        Long idClube,
        String nomeClube,
        Integer jogos,
        Integer vitorias,
        Integer empates,
        Integer derrotas,
        Integer golsMarcados,
        Integer golsSofridos,
        Integer saldoGols,
        Integer pontos

){
}
