package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.RetrospectoGeralDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetrospectoService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;


    public RetrospectoGeralDTO calcularetrospectoGeral(Long idClube){

        Clube clube = clubeRepository.findById(idClube)
                .orElseThrow(()-> new Exceptions.ClubeInvalidoeException(utils.mensagensException.CLUBE_INEXISTENTE));

        List<Partida> partidas = partidaRepository.findAllByClubeList(idClube);

        if(partidas.isEmpty()){
            return  new RetrospectoGeralDTO(
                    clube.getIdClube(),
                    clube.getNome(),
                    0, // jogos
                    0, // vitórias
                    0, // empates
                    0, // derrotas
                    0, // gols marcados
                    0, // gols sofridos
                    0, // saldo
                    0// ponto

            );

        }


        int jogos = 0;
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;
        int golsMarcados = 0;
        int golsSofridos = 0;

        for(Partida p : partidas){
            jogos++;

            boolean mandante = p.getClubeMandante().getIdClube().equals(idClube);

            int golsPro = mandante ? p.getGolsMandante() : p.getGolsVisitante();
            int golsContra = mandante ? p.getGolsVisitante() : p.getGolsMandante();

            golsMarcados += golsPro;
            golsSofridos += golsContra;

            if(golsPro > golsContra){
                vitorias++;

            } else if (golsPro == golsContra) {
                empates++;

            }else {
                derrotas++;

            }

        }

        int saldoGols = golsMarcados - golsSofridos;
        int pontos = vitorias * 3 + empates;

        return new RetrospectoGeralDTO(
                clube.getIdClube(),
                clube.getNome(),
                jogos,
                vitorias,
                empates,
                derrotas,
                golsMarcados,
                golsSofridos,
                saldoGols,
                pontos
        );


    }

}
