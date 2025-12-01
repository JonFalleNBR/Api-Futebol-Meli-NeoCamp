package com.jonathan.futebol_api.adapter.dto;

public record RetrospectoAdversariosDTO(
        Long idAdversario,
        String nomeAdversario,
        Integer jogos,
        Integer vitorias,
        Integer empates,
        Integer derrotas,
        Integer golsMarcados,
        Integer golsSofridos,
        Integer saldoGols,
        Double aproveitamento

) {
}
