package com.jonathan.futebol_api.adapter.dto;

import java.util.List;

public record RetrospectoConfrontoDiretoDTO(
        Long idClube1,
        String nomeClube1,
        Long idClube2,
        String nomeClube2,

        Integer jogos,
        Integer vitoriasClube1,
        Integer vitoriasClube2,
        Integer empates,

        Integer golsClube1,
        Integer golsClube2,
        Integer saldoClube1,
        Integer saldoClube2,

        Integer pontosClube1,
        Integer pontosClube2,
        Double aproveitamentoClube1,   // em %
        Double aproveitamentoClube2,   // em %

        List<PartidaResponseDTO> partidas // lista de partidas entre os dois

) {
}
